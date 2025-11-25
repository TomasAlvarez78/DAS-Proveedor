package ubp.edu.com.ar.finalproyect.port;

import java.util.Map;

/**
 * Port interface for Pedido (Order) operations
 * Defines contracts for database access using stored procedures
 */
public interface PedidoRepository {

    /**
     * Estimate if an order can be executed
     * @param productosJson JSON array with products [{codigoBarra, cantidad}, ...]
     * @return Map with fechaEstimada, precioEstimadoTotal, and productosJson
     */
    Map<String, Object> estimarPedido(String productosJson);

    /**
     * Confirm and register an order to the database
     * @param idCliente Client ID
     * @param productosJson JSON array with products [{codigoBarra, cantidad}, ...]
     * @return Map with idPedido, estadoPedido, fechaEstimada, and precioTotal
     */
    Map<String, Object> asignarPedido(int idCliente, String productosJson);

    /**
     * Get order status and details
     * @param idPedido Order ID
     * @return Map with idPedido and estado, or error message
     */
    Map<String, Object> consultarEstado(long idPedido);

    /**
     * Cancel an order
     * @param idPedido Order ID to cancel
     * @return Map with idPedido, estado, and mensaje
     */
    Map<String, Object> cancelarPedido(long idPedido);

    /**
     * Update order status
     * @param idPedido Order ID
     * @param estadoId New status ID
     * @return Map with mensaje
     */
    Map<String, Object> updatePedidoStatus(long idPedido, int estadoId);
}
