CREATE TABLE `EstadoPedido`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` TINYTEXT NOT NULL,
    `descripcion` TEXT NULL
);
CREATE TABLE `Proveedor`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` TINYTEXT NOT NULL,
    `apikey` VARCHAR(255) NOT NULL,
    `servicio` TEXT NOT NULL,
    `tipoServicio` SMALLINT NOT NULL
);
CREATE TABLE `TipoServicio`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` TEXT NOT NULL
);
CREATE TABLE `Producto`(
    `codigoBarra` SMALLINT NOT NULL,
    `nombre` TEXT NOT NULL,
    `imagen` BLOB NULL,
    `stockMinimo` SMALLINT NOT NULL,
    `stockMaximo` SMALLINT NOT NULL,
    `stockActual` SMALLINT NOT NULL,
    PRIMARY KEY(`codigoBarra`)
);
CREATE TABLE `Pedido`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `estado` SMALLINT NOT NULL,
    `proveedor` SMALLINT NOT NULL,
    `fechaEstimada` DATETIME NOT NULL,
    `fechaEntrega` DATETIME NOT NULL,
    `fechaRegistro` DATETIME NOT NULL,
    `evaluacionEscala` SMALLINT NOT NULL
);
CREATE TABLE `PedidoProducto`(
    `idPedido` SMALLINT NOT NULL,
    `codigoBarra` SMALLINT NOT NULL,
    `cantidad` SMALLINT NOT NULL,
    PRIMARY KEY(`idPedido`)
);
ALTER TABLE
    `PedidoProducto` ADD PRIMARY KEY(`codigoBarra`);
CREATE TABLE `ProductoProveedor`(
    `codigoBarra` SMALLINT NOT NULL,
    `fechaActualizacion` DATETIME NOT NULL,
    `IdProveedor` SMALLINT NOT NULL,
    `estado` SMALLINT NOT NULL,
    PRIMARY KEY(`codigoBarra`)
);
ALTER TABLE
    `ProductoProveedor` ADD PRIMARY KEY(`IdProveedor`);
CREATE TABLE `EstadoProducto`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` TINYTEXT NOT NULL,
    `descripcion` TEXT NULL
);
CREATE TABLE `Escala`(
    `idEscala` SMALLINT NOT NULL,
    `idProveedor` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `escalaInt` VARCHAR(255) NOT NULL,
    `escalaExt` VARCHAR(255) NOT NULL,
    `descripcionExt` VARCHAR(255) NULL,
    PRIMARY KEY(`idEscala`)
);
CREATE TABLE `HistorialPrecio`(
    `codigoBarra` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `idProveedor` BIGINT NOT NULL,
    `precio` FLOAT(53) NOT NULL,
    `fechaInicio` DATETIME NOT NULL,
    `fechaFin` DATETIME NULL,
    PRIMARY KEY(`idProveedor`)
);
CREATE TABLE `Usuario`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `permisos` SMALLINT NOT NULL
);
ALTER TABLE
    `Producto` ADD CONSTRAINT `producto_codigobarra_foreign` FOREIGN KEY(`codigoBarra`) REFERENCES `PedidoProducto`(`codigoBarra`);
ALTER TABLE
    `Proveedor` ADD CONSTRAINT `proveedor_tiposervicio_foreign` FOREIGN KEY(`tipoServicio`) REFERENCES `TipoServicio`(`id`);
ALTER TABLE
    `Pedido` ADD CONSTRAINT `pedido_id_foreign` FOREIGN KEY(`id`) REFERENCES `PedidoProducto`(`idPedido`);
ALTER TABLE
    `Pedido` ADD CONSTRAINT `pedido_estado_foreign` FOREIGN KEY(`estado`) REFERENCES `EstadoPedido`(`id`);
ALTER TABLE
    `Pedido` ADD CONSTRAINT `pedido_proveedor_foreign` FOREIGN KEY(`proveedor`) REFERENCES `Proveedor`(`id`);
ALTER TABLE
    `Escala` ADD CONSTRAINT `escala_idproveedor_foreign` FOREIGN KEY(`idProveedor`) REFERENCES `Proveedor`(`id`);
ALTER TABLE
    `ProductoProveedor` ADD CONSTRAINT `productoproveedor_idproveedor_foreign` FOREIGN KEY(`IdProveedor`) REFERENCES `Proveedor`(`id`);
ALTER TABLE
    `Producto` ADD CONSTRAINT `producto_codigobarra_foreign` FOREIGN KEY(`codigoBarra`) REFERENCES `HistorialPrecio`(`codigoBarra`);
ALTER TABLE
    `Pedido` ADD CONSTRAINT `pedido_evaluacionescala_foreign` FOREIGN KEY(`evaluacionEscala`) REFERENCES `Escala`(`idEscala`);
ALTER TABLE
    `ProductoProveedor` ADD CONSTRAINT `productoproveedor_idproveedor_foreign` FOREIGN KEY(`IdProveedor`) REFERENCES `HistorialPrecio`(`idProveedor`);
ALTER TABLE
    `ProductoProveedor` ADD CONSTRAINT `productoproveedor_estado_foreign` FOREIGN KEY(`estado`) REFERENCES `EstadoProducto`(`id`);
ALTER TABLE
    `ProductoProveedor` ADD CONSTRAINT `productoproveedor_codigobarra_foreign` FOREIGN KEY(`codigoBarra`) REFERENCES `Producto`(`codigoBarra`);