package ubp.edu.com.ar.finalproyect.port;

import ubp.edu.com.ar.finalproyect.domain.Clientes;
import ubp.edu.com.ar.finalproyect.domain.Producto;
import ubp.edu.com.ar.finalproyect.domain.Pedido;
import java.util.List;
import java.util.Map;

/**
 * Port interface for Proveedor (Provider) operations
 * Defines contracts for database access using stored procedures
 */
public interface ProveedorRepository {

    /**
     * Health check - validate database connection
     * @return "OK" if connection is successful
     */
    String health();

    /**
     * Get all products with current prices
     * @return List of all products
     */
    List<Producto> getProductos();

    /**
     * Get client by API key for authentication
     * @param apiKey The API key to validate
     * @return Clientes object if found, null otherwise
     */
    Clientes getClienteByApiKey(String apiKey);

    /**
     * Estimate if an order can be executed
     * @param idCliente Client ID
     * @param productosJson JSON array with products and quantities
     * @return Map with fechaEstimada, precioEstimadoTotal, and productosJson
     */
    Map<String, Object> estimarPedido(int idCliente, String productosJson);

    /**
     * Confirm and register an order to the database
     * @param idCliente Client ID
     * @param productosJson JSON array with products and quantities
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
}
