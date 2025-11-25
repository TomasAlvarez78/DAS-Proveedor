package ubp.edu.com.ar.finalproyect.service;

import org.springframework.stereotype.Service;
import ubp.edu.com.ar.finalproyect.port.PedidoRepository;

import java.util.Map;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Build and order without creating it
     * returns estimated values and product availability
     */
    public Map<String, Object> estimarPedido(String productosJson) {
        if (productosJson == null || productosJson.isEmpty()) {
            throw new IllegalArgumentException("Productos JSON cannot be empty");
        }
        return pedidoRepository.estimarPedido(productosJson);
    }

    /**
     * Build and order, creating it and assigning it to a client
     * returns orderId if created correctly
     */
    public Map<String, Object> asignarPedido(int idCliente, String productosJson) {
        if (productosJson == null || productosJson.isEmpty()) {
            throw new IllegalArgumentException("Productos JSON cannot be empty");
        }
        return pedidoRepository.asignarPedido(idCliente, productosJson);
    }

    /**
     * Get order status
     * returns idOrder and status value
     */
    public Map<String, Object> consultarEstado(long idPedido) {
        if (idPedido <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }
        return pedidoRepository.consultarEstado(idPedido);
    }

    /**
     * Cancel order
     * returns idOrder and status value
     */
    public Map<String, Object> cancelarPedido(long idPedido) {
        if (idPedido <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }
        return pedidoRepository.cancelarPedido(idPedido);
    }

    /**
     * INTERNAL USE ONLY
     * Validate if superuser, then change order's status
     * returns idOrder and status value
     */
    public Map<String, Object> updatePedidoStatus(long idPedido, int estadoId) {
        if (idPedido <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }
        if (estadoId <= 0) {
            throw new IllegalArgumentException("Invalid estado ID");
        }
        return pedidoRepository.updatePedidoStatus(idPedido, estadoId);
    }
}
