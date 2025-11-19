-- =============================================
-- Proveedor Database Schema
-- =============================================

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
-- =============================================
CREATE TABLE EstadoPedido (
    id SMALLINT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(255) NOT NULL,
    descripcion NVARCHAR(500) NOT NULL
);

-- =============================================
-- Clientes Table
-- =============================================
CREATE TABLE Clientes (
    id SMALLINT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(255) NOT NULL,
    descripcion NVARCHAR(500) NOT NULL,
    apiKey NVARCHAR(500) NOT NULL,
    servicio NVARCHAR(255) NOT NULL
);

-- =============================================
-- Ponderacion Table
-- =============================================
CREATE TABLE Ponderacion (
    id SMALLINT IDENTITY(1,1) PRIMARY KEY,
    puntuacion SMALLINT NOT NULL,
    descripcion NVARCHAR(500) NULL
);

-- =============================================
-- Producto Table
-- =============================================
CREATE TABLE Producto (
    id SMALLINT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(500) NOT NULL,
    imagen NVARCHAR(500) NOT NULL,
    stock SMALLINT NOT NULL,
    precio FLOAT NOT NULL
);

-- =============================================
-- Pedido Table
-- =============================================
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
-- =============================================
CREATE TABLE PedidoProducto (
    id SMALLINT IDENTITY(1,1) PRIMARY KEY,
    codigoBarra SMALLINT NOT NULL,
    cantidad SMALLINT NOT NULL,
    precioPedido BIGINT NOT NULL,
    idPedido BIGINT NOT NULL,
    CONSTRAINT FK_PedidoProducto_Producto FOREIGN KEY (codigoBarra) REFERENCES Producto(id),
    CONSTRAINT FK_PedidoProducto_Pedido FOREIGN KEY (idPedido) REFERENCES Pedido(id)
);

-- =============================================
-- Insert Reference Data
-- =============================================

-- Insert order statuses
INSERT INTO EstadoPedido (nombre, descripcion) VALUES ('Asignado', 'Pedido en espera de confirmacion');
INSERT INTO EstadoPedido (nombre, descripcion) VALUES ('En Proceso', 'Pedido procesandose');
INSERT INTO EstadoPedido (nombre, descripcion) VALUES ('En camino', 'Pedido en camino');
INSERT INTO EstadoPedido (nombre, descripcion) VALUES ('Cancelado', 'Pedido cancelado');
INSERT INTO EstadoPedido (nombre, descripcion) VALUES ('Finalizado', 'Pedido finalizado');

-- Insert rating scales
INSERT INTO Ponderacion (puntuacion, descripcion) VALUES (1, 'Muy Insatisfecho');
INSERT INTO Ponderacion (puntuacion, descripcion) VALUES (2, 'Insatisfecho');
INSERT INTO Ponderacion (puntuacion, descripcion) VALUES (3, 'Neutral');
INSERT INTO Ponderacion (puntuacion, descripcion) VALUES (4, 'Satisfecho');
INSERT INTO Ponderacion (puntuacion, descripcion) VALUES (5, 'Muy Satisfecho');

PRINT 'Database created';
GO
