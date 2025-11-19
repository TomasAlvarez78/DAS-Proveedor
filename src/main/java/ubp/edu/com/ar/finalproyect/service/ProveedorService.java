package ubp.edu.com.ar.finalproyect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubp.edu.com.ar.finalproyect.domain.Clientes;
import ubp.edu.com.ar.finalproyect.domain.Producto;
import ubp.edu.com.ar.finalproyect.port.ProveedorRepository;
import java.util.List;
import java.util.Map;

/**
 * Service layer for Proveedor operations
 * Contains business logic and validation
 */
@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Autowired
    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    /**
     * Get all products with current prices
     * @return List of all products
     */
    public List<Producto> getProductos() {
        return proveedorRepository.getProductos();
    }

    /**
     * Validate API key and get client information
     * @param apiKey The API key to validate
     * @return Clientes object if valid, null otherwise
     */
    public Clientes validateApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            return null;
        }
        return proveedorRepository.getClienteByApiKey(apiKey);
    }

    /**
     * Estimate if an order can be executed
     * @param idCliente Client ID
     * @param productosJson JSON array with products [{codigoBarra, cantidad}, ...]
     * @return Map with estimation details or error
     */
    public Map<String, Object> estimarPedido(int idCliente, String productosJson) {
        try {
            if (productosJson == null || productosJson.isEmpty()) {
                throw new IllegalArgumentException("Productos JSON cannot be empty");
            }
            return proveedorRepository.estimarPedido(idCliente, productosJson);
        } catch (Exception e) {
            throw new RuntimeException("Error estimating order: " + e.getMessage(), e);
        }
    }

    /**
     * Confirm and register an order
     * @param idCliente Client ID
     * @param productosJson JSON array with products [{codigoBarra, cantidad}, ...]
     * @return Map with order confirmation or error
     */
    public Map<String, Object> asignarPedido(int idCliente, String productosJson) {
        try {
            if (productosJson == null || productosJson.isEmpty()) {
                throw new IllegalArgumentException("Productos JSON cannot be empty");
            }
            return proveedorRepository.asignarPedido(idCliente, productosJson);
        } catch (Exception e) {
            throw new RuntimeException("Error assigning order: " + e.getMessage(), e);
        }
    }

    /**
     * Get order status
     * @param idPedido Order ID
     * @return Map with order status or error
     */
    public Map<String, Object> consultarEstado(long idPedido) {
        try {
            if (idPedido <= 0) {
                throw new IllegalArgumentException("Invalid order ID");
            }
            return proveedorRepository.consultarEstado(idPedido);
        } catch (Exception e) {
            throw new RuntimeException("Error consulting order status: " + e.getMessage(), e);
        }
    }

    /**
     * Cancel an order
     * @param idPedido Order ID to cancel
     * @return Map with cancellation result or error
     */
    public Map<String, Object> cancelarPedido(long idPedido) {
        try {
            if (idPedido <= 0) {
                throw new IllegalArgumentException("Invalid order ID");
            }
            return proveedorRepository.cancelarPedido(idPedido);
        } catch (Exception e) {
            throw new RuntimeException("Error canceling order: " + e.getMessage(), e);
        }
    }
}
