CREATE TABLE `EstadoPedido`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` TINYTEXT NOT NULL,
    `descripcion` TEXT NOT NULL
);
CREATE TABLE `Pedido`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `estado` SMALLINT NOT NULL,
    `puntuacion` TINYINT NOT NULL,
    `fechaEstimada` DATETIME NOT NULL,
    `fechaEntrega` DATETIME NOT NULL,
    `fechaRegistro` DATETIME NOT NULL,
    `cliente` SMALLINT NOT NULL
);
CREATE TABLE `Clientes`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(255) NOT NULL,
    `descripcion` TEXT NOT NULL,
    `apiKey` TEXT NOT NULL,
    `servicio` VARCHAR(255) NOT NULL
);
CREATE TABLE `PedidoProducto`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `codigoBarra` SMALLINT NOT NULL,
    `cantidad` SMALLINT NOT NULL,
    `precioPedido` BIGINT NOT NULL,
    PRIMARY KEY(`codigoBarra`)
);
CREATE TABLE `Producto`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` TEXT NOT NULL,
    `imagen` BLOB NOT NULL,
    `stock` SMALLINT NOT NULL,
    `precio` FLOAT(53) NOT NULL
);
CREATE TABLE `Ponderacion`(
    `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `puntuacion` SMALLINT NOT NULL,
    `descripcion` SMALLINT NULL
);
ALTER TABLE
    `Pedido` ADD CONSTRAINT `pedido_id_foreign` FOREIGN KEY(`id`) REFERENCES `PedidoProducto`(`id`);
ALTER TABLE
    `Producto` ADD CONSTRAINT `producto_id_foreign` FOREIGN KEY(`id`) REFERENCES `PedidoProducto`(`codigoBarra`);
ALTER TABLE
    `Pedido` ADD CONSTRAINT `pedido_puntuacion_foreign` FOREIGN KEY(`puntuacion`) REFERENCES `Ponderacion`(`puntuacion`);
ALTER TABLE
    `Pedido` ADD CONSTRAINT `pedido_cliente_foreign` FOREIGN KEY(`cliente`) REFERENCES `Clientes`(`id`);
ALTER TABLE
    `Pedido` ADD CONSTRAINT `pedido_estado_foreign` FOREIGN KEY(`estado`) REFERENCES `EstadoPedido`(`id`);