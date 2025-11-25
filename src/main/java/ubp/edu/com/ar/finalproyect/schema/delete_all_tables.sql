-- Drop tables
IF OBJECT_ID('PedidoProducto', 'U') IS NOT NULL DROP TABLE PedidoProducto;
IF OBJECT_ID('Pedido', 'U') IS NOT NULL DROP TABLE Pedido;
IF OBJECT_ID('EstadoPedido', 'U') IS NOT NULL DROP TABLE EstadoPedido;
IF OBJECT_ID('Producto', 'U') IS NOT NULL DROP TABLE Producto;
IF OBJECT_ID('Ponderacion', 'U') IS NOT NULL DROP TABLE Ponderacion;
IF OBJECT_ID('Clientes', 'U') IS NOT NULL DROP TABLE Clientes;
GO