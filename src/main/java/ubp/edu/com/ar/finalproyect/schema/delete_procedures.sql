-- Delete store procedures
IF OBJECT_ID('sp_get_productos', 'P') IS NOT NULL DROP PROCEDURE sp_get_productos;
IF OBJECT_ID('sp_get_cliente_by_apikey', 'P') IS NOT NULL DROP PROCEDURE sp_get_cliente_by_apikey;
IF OBJECT_ID('sp_estimar_pedido', 'P') IS NOT NULL DROP PROCEDURE sp_estimar_pedido;
IF OBJECT_ID('sp_asignar_pedido', 'P') IS NOT NULL DROP PROCEDURE sp_asignar_pedido;
IF OBJECT_ID('sp_consultar_estado', 'P') IS NOT NULL DROP PROCEDURE sp_consultar_estado;
IF OBJECT_ID('sp_cancelar_pedido', 'P') IS NOT NULL DROP PROCEDURE sp_cancelar_pedido;
IF OBJECT_ID('sp_update_pedido_status', 'P') IS NOT NULL DROP PROCEDURE sp_update_pedido_status;
GO