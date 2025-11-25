-- Inserts

-- Insert order statuses
INSERT INTO EstadoPedido (nombre, descripcion) VALUES ('Asignado', 'Pedido en espera de confirmacion');
INSERT INTO EstadoPedido (nombre, descripcion) VALUES ('En Proceso', 'Pedido procesandose');
INSERT INTO EstadoPedido (nombre, descripcion) VALUES ('En camino', 'Pedido en camino');
INSERT INTO EstadoPedido (nombre, descripcion) VALUES ('Cancelado', 'Pedido cancelado');
INSERT INTO EstadoPedido (nombre, descripcion) VALUES ('Finalizado', 'Pedido finalizado');

-- Insert puntuacion
INSERT INTO Ponderacion (puntuacion, descripcion) VALUES (1, 'Muy Insatisfecho');
INSERT INTO Ponderacion (puntuacion, descripcion) VALUES (2, 'Insatisfecho');
INSERT INTO Ponderacion (puntuacion, descripcion) VALUES (3, 'Neutral');
INSERT INTO Ponderacion (puntuacion, descripcion) VALUES (4, 'Satisfecho');
INSERT INTO Ponderacion (puntuacion, descripcion) VALUES (5, 'Muy Satisfecho');

INSERT INTO Producto (nombre, imagen, stock, precio) VALUES
                                                         ('Leche Entera', 'https://placeholder.com/leche.jpg', 300, 1500.00),
                                                         ('Queso Cheddar', 'https://placeholder.com/queso.jpg', 80, 8000.00),
                                                         ('Pan Integral Rebanado', 'https://placeholder.com/pan.jpg', 150, 3200.00),
                                                         ('Docena de Huevos', 'https://placeholder.com/huevos.jpg', 250, 5800.00),
                                                         ('Manzanas Rojas', 'https://placeholder.com/manzanas.jpg', 100, 4900.00);
GO

INSERT INTO Clientes (clientIdentifier, nombre, descripcion, apiKey, servicio) VALUES
     ('supercaracol', 'SuperCaracol', 'Super Caracol con integracion SOAP', '$2a$10$vI8aWBnW3fID.ZQ4/zo1G.q1lRps.9cGQEXqvGx5a.KsZ1JYqKtVe', 'REST'),
     ('supergalicia', 'SuperGalicia', 'Super Galicia con integracion REST', '$2a$10$9kFQQ7Z9o4pqZjCxMbGI3.YxMHvHMX8qzL4RqKQJYz7PQqXYsGFKO', 'REST'),
     ('supervea', 'SuperVea', 'Super Vea con integracion REST', '$2a$10$Hx3Ue7P6gFV8nXqQxGJTj.fHwQvXY6hQVZ9LpXF8rYzQXPzVmNKJC', 'REST');
GO

