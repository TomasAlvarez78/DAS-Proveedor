-- =============================================
-- Proveedor Stored Procedures
-- =============================================

-- =============================================
-- 1. sp_get_productos - Return all products with current prices
-- Returns: All products available
-- =============================================
CREATE OR ALTER PROCEDURE sp_get_productos
AS
BEGIN
    SELECT
        id as codigoBarra,
        nombre,
        precio
    FROM Producto
    ORDER BY nombre;
END
GO

-- =============================================
-- 2. sp_get_cliente_by_apikey - Get client by API key (for validation)
-- Input: @apiKey
-- Returns: Client ID and details if found
-- =============================================
CREATE OR ALTER PROCEDURE sp_get_cliente_by_apikey
    @apiKey NVARCHAR(MAX)
AS
BEGIN
    SELECT
        id,
        nombre,
        descripcion,
        apiKey,
        servicio
    FROM Clientes
    WHERE apiKey = @apiKey;
END
GO

-- =============================================
-- 3. sp_estimar_pedido - Estimate if order can be executed
-- Input: @idCliente, @productosJson (JSON array with codigoBarra and cantidad)
-- Returns: Estimated date, total price, and availability for each product
--
-- JSON Format:
-- [{"codigoBarra": 1, "cantidad": 3}, {"codigoBarra": 2, "cantidad": 2}]
-- =============================================
CREATE OR ALTER PROCEDURE sp_estimar_pedido
    @idCliente SMALLINT,
    @productosJson NVARCHAR(MAX)
AS
BEGIN
    DECLARE @index INT = 0;
    DECLARE @count INT;
    DECLARE @codigoBarra SMALLINT;
    DECLARE @cantidad SMALLINT;
    DECLARE @precio FLOAT;
    DECLARE @precioEstimadoTotal FLOAT = 0;
    DECLARE @fechaEstimada DATETIME = DATEADD(day, 3, GETDATE());
    DECLARE @productosResultJson NVARCHAR(MAX) = '[]';

    -- Validate JSON
    IF ISJSON(@productosJson) = 0
    BEGIN
        SELECT 'Error: Formato JSON inv�lido' as error;
        RETURN;
    END

    -- Get count of products
    SET @count = (SELECT COUNT(*) FROM OPENJSON(@productosJson));

    -- Validate client exists
    IF NOT EXISTS (SELECT 1 FROM Clientes WHERE id = @idCliente)
    BEGIN
        SELECT 'Error: Cliente no encontrado' as error;
        RETURN;
    END

    -- Loop through each product
    SET @index = 0;
    WHILE @index < @count
    BEGIN
        -- Extract product data from JSON, oh god
        SELECT
            @codigoBarra = CAST(JSON_VALUE(@productosJson, CONCAT('$[', @index, '].codigoBarra')) AS SMALLINT),
            @cantidad = CAST(JSON_VALUE(@productosJson, CONCAT('$[', @index, '].cantidad')) AS SMALLINT);

        -- Get product price from database
        SELECT @precio = precio FROM Producto WHERE id = @codigoBarra;

        -- If product exists, calculate price and add availability status, im going crazy
        IF @precio IS NOT NULL
        BEGIN
            SET @precioEstimadoTotal = @precioEstimadoTotal + (@precio * @cantidad);

            -- Build products result JSON by concatenating
            IF @index = 0
                SET @productosResultJson = CONCAT('[{"codigoBarra":', @codigoBarra, ',"nombre":"', (SELECT nombre FROM Producto WHERE id = @codigoBarra), '","available":1}');
            ELSE
                SET @productosResultJson = CONCAT(LEFT(@productosResultJson, LEN(@productosResultJson)-1), ',{"codigoBarra":', @codigoBarra, ',"nombre":"', (SELECT nombre FROM Producto WHERE id = @codigoBarra), '","available":1}]');

            SET @precio = NULL;
        END

        SET @index = @index + 1;
    END

    -- Return estimation result yeay
    SELECT
        @fechaEstimada as fechaEstimada,
        @precioEstimadoTotal as precioEstimadoTotal,
        @productosResultJson as productosJson;
END
GO

-- =============================================
-- 4. sp_asignar_pedido - Confirm and register multi-product order
-- Input: @idCliente, @productosJson
-- Returns: Order confirmation with idPedido, total price, and status
--
-- Creates: Pedido record + PedidoProducto records for each product
-- =============================================
CREATE OR ALTER PROCEDURE sp_asignar_pedido
    @idCliente SMALLINT,
    @productosJson NVARCHAR(MAX)
AS
BEGIN
    DECLARE @index INT = 0;
    DECLARE @count INT;
    DECLARE @codigoBarra SMALLINT;
    DECLARE @cantidad SMALLINT;
    DECLARE @precio FLOAT;
    DECLARE @precioPedido FLOAT;
    DECLARE @precioTotal FLOAT = 0;
    DECLARE @idPedido BIGINT;
    DECLARE @estadoPendiente SMALLINT = 1;
    DECLARE @fechaEstimada DATETIME = DATEADD(day, 3, GETDATE());
    DECLARE @fechaEntrega DATETIME = DATEADD(day, 3, GETDATE());
    DECLARE @fechaRegistro DATETIME = GETDATE();
    DECLARE @puntuacion TINYINT = 0;

    -- Validate JSON
    IF ISJSON(@productosJson) = 0
    BEGIN
        SELECT 'Error: Formato JSON invalido' as mensaje, NULL as idPedido;
        RETURN;
    END

    -- Validate client exists
    IF NOT EXISTS (SELECT 1 FROM Clientes WHERE id = @idCliente)
    BEGIN
        SELECT 'Error: Cliente no encontrado' as mensaje, NULL as idPedido;
        RETURN;
    END

    -- Get count of products
    SET @count = (SELECT COUNT(*) FROM OPENJSON(@productosJson));

    -- Validate all products exist and have stock with JSON, nooo
    SET @index = 0;
    WHILE @index < @count
    BEGIN
        SELECT
            @codigoBarra = CAST(JSON_VALUE(@productosJson, CONCAT('$[', @index, '].codigoBarra')) AS SMALLINT),
            @cantidad = CAST(JSON_VALUE(@productosJson, CONCAT('$[', @index, '].cantidad')) AS SMALLINT);

        -- Check if product exists and has sufficient stock
        IF NOT EXISTS (SELECT 1 FROM Producto WHERE id = @codigoBarra AND stock >= @cantidad)
        BEGIN
            SELECT 'Error: Productos no disponibles o stock insuficiente' as mensaje, NULL as idPedido;
            RETURN;
        END

        SET @index = @index + 1;
    END

    -- Create the order, finally
    INSERT INTO Pedido (estado, puntuacion, fechaEstimada, fechaEntrega, fechaRegistro, cliente)
    VALUES (@estadoPendiente, @puntuacion, @fechaEstimada, @fechaEntrega, @fechaRegistro, @idCliente);

    SET @idPedido = SCOPE_IDENTITY();

    -- Loop through products and insert order items, calculate total price with JSON again
    SET @index = 0;
    WHILE @index < @count
    BEGIN
        SELECT
            @codigoBarra = CAST(JSON_VALUE(@productosJson, CONCAT('$[', @index, '].codigoBarra')) AS SMALLINT),
            @cantidad = CAST(JSON_VALUE(@productosJson, CONCAT('$[', @index, '].cantidad')) AS SMALLINT);

        -- Get product price
        SELECT @precio = precio FROM Producto WHERE id = @codigoBarra;

        -- Calculate price for this product
        SET @precioPedido = @precio * @cantidad;
        SET @precioTotal = @precioTotal + @precioPedido;

        -- Insert product into order
        INSERT INTO PedidoProducto (codigoBarra, cantidad, precioPedido, idPedido)
        VALUES (@codigoBarra, @cantidad, @precioPedido, @idPedido);

        SET @index = @index + 1;
    END

    -- Return confirmation
    SELECT
        @idPedido as idPedido,
        'Asignado' as estadoPedido,
        @fechaEstimada as fechaEstimada,
        @precioTotal as precioTotal;
END
GO

-- =============================================
-- 5. sp_consultar_estado - Get order status
-- Input: @idPedido
-- Returns: Order status and details, or error if not found
-- =============================================
CREATE OR ALTER PROCEDURE sp_consultar_estado
    @idPedido BIGINT
AS
BEGIN
    -- Check if order exists
    IF NOT EXISTS (SELECT 1 FROM Pedido WHERE id = @idPedido)
    BEGIN
        SELECT
            @idPedido as idPedido,
            NULL as estado,
            'Pedido no encontrado' as descripcion;
        RETURN;
    END

    -- Return order status
    SELECT
        p.id as idPedido,
        ep.nombre as estado
    FROM Pedido p
    INNER JOIN EstadoPedido ep ON p.estado = ep.id
    WHERE p.id = @idPedido;
END
GO

-- =============================================
-- 6. sp_cancelar_pedido - Cancel an order
-- Input: @idPedido
-- Returns: Success/error message and updated stats
--
-- Rules: Can only cancel if order is in state 1 (Asignado)
-- Cannot cancel if status is >= 2 (En Proceso, En camino, Entregado, Cancelado)
-- =============================================
CREATE OR ALTER PROCEDURE sp_cancelar_pedido
    @idPedido BIGINT
AS
BEGIN
    DECLARE @estadoActual SMALLINT;
    DECLARE @estadoNoCancelables SMALLINT = 2;

    -- Get current order status
    SELECT @estadoActual = estado FROM Pedido WHERE id = @idPedido;

    -- Check if order exists
    IF @estadoActual IS NULL
    BEGIN
        SELECT
            @idPedido as idPedido,
            NULL as estado,
            'Pedido no encontrado' as descripcion;
        RETURN;
    END

    -- Check if order can be cancelled
    IF @estadoActual >= @estadoNoCancelables
    BEGIN
        SELECT
            @idPedido as idPedido,
            (SELECT nombre FROM EstadoPedido WHERE id = @estadoActual) as estado,
            'No se puede cancelar un pedido que ya fue enviado' as descripcion;
        RETURN;
    END

    -- Cancel the order
    UPDATE Pedido
    SET estado = @estadoCancelado
    WHERE id = @idPedido;

    -- Return success
    SELECT
        @idPedido as idPedido,
        'Cancelado' as estado;
END
GO

-- =============================================
-- Helper Procedures
-- =============================================

-- sp_update_pedido_status - Update order status
CREATE OR ALTER PROCEDURE sp_update_pedido_status
    @idPedido BIGINT,
    @estadoId SMALLINT
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM EstadoPedido WHERE id = @estadoId)
    BEGIN
        SELECT 'Error: Estado no valido' as mensaje;
        RETURN;
    END

    IF NOT EXISTS (SELECT 1 FROM Pedido WHERE id = @idPedido)
    BEGIN
        SELECT 'Error: Pedido no encontrado' as mensaje;
        RETURN;
    END

    UPDATE Pedido
    SET estado = @estadoId
    WHERE id = @idPedido;

    SELECT 'Estado actualizado exitosamente' as mensaje;
END
GO
