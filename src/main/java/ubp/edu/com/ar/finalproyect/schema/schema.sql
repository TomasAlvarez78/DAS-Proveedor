-- Proveedor Database Schema

-- Drop tables
IF OBJECT_ID('PedidoProducto', 'U') IS NOT NULL DROP TABLE PedidoProducto;
IF OBJECT_ID('Pedido', 'U') IS NOT NULL DROP TABLE Pedido;
IF OBJECT_ID('EstadoPedido', 'U') IS NOT NULL DROP TABLE EstadoPedido;
IF OBJECT_ID('Producto', 'U') IS NOT NULL DROP TABLE Producto;
IF OBJECT_ID('Ponderacion', 'U') IS NOT NULL DROP TABLE Ponderacion;
IF OBJECT_ID('Clientes', 'U') IS NOT NULL DROP TABLE Clientes;
GO

-- =============================================
-- EstadoPedido Table
CREATE TABLE EstadoPedido (
    id SMALLINT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(255) NOT NULL,
    descripcion NVARCHAR(500) NOT NULL
);

-- =============================================
-- Clientes Table
CREATE TABLE Clientes (
    id SMALLINT IDENTITY(1,1) PRIMARY KEY,
    clientIdentifier NVARCHAR(50) NOT NULL UNIQUE,
    nombre NVARCHAR(255) NOT NULL,
    descripcion NVARCHAR(500) NOT NULL,
    apiKey NVARCHAR(60) NOT NULL,
    servicio NVARCHAR(255) NOT NULL
);

-- =============================================
-- Ponderacion Table
CREATE TABLE Ponderacion (
    id SMALLINT IDENTITY(1,1) PRIMARY KEY,
    puntuacion SMALLINT NOT NULL,
    descripcion NVARCHAR(500) NULL
);

-- =============================================
-- Producto Table
CREATE TABLE Producto (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(500) NOT NULL,
    imagen NVARCHAR(500) NOT NULL,
    stock SMALLINT NOT NULL,
    precio FLOAT NOT NULL
);

-- =============================================
-- Pedido Table
CREATE TABLE Pedido (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    estado SMALLINT NOT NULL,
    puntuacion TINYINT NOT NULL,
    fechaEstimada DATETIME NOT NULL,
    fechaEntrega DATETIME NOT NULL,
    fechaRegistro DATETIME NOT NULL,
    cliente SMALLINT NOT NULL,
    CONSTRAINT FK_Pedido_EstadoPedido FOREIGN KEY (estado) REFERENCES EstadoPedido(id),
    CONSTRAINT FK_Pedido_Clientes FOREIGN KEY (cliente) REFERENCES Clientes(id)
);

-- =============================================
-- PedidoProducto Table
CREATE TABLE PedidoProducto (
    id SMALLINT IDENTITY(1,1) PRIMARY KEY,
    codigoBarra INT NOT NULL,
    cantidad SMALLINT NOT NULL,
    precioPedido FLOAT NOT NULL,
    idPedido BIGINT NOT NULL,
    CONSTRAINT FK_PedidoProducto_Producto FOREIGN KEY (codigoBarra) REFERENCES Producto(id),
    CONSTRAINT FK_PedidoProducto_Pedido FOREIGN KEY (idPedido) REFERENCES Pedido(id)
);

PRINT 'Database created';
GO
