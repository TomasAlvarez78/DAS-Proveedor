package ubp.edu.com.ar.finalproyect.service;

import org.springframework.stereotype.Service;
import ubp.edu.com.ar.finalproyect.port.PedidoRepository;

import java.util.List;
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

    /**
     * Rate an order
     * returns idOrder and description
     */
    public Map<String, Object> puntuarPedido(long idPedido, int puntuacion) {
        if (idPedido <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }
        if (puntuacion < 1 || puntuacion > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        return pedidoRepository.puntuarPedido(idPedido, puntuacion);
    }

    /**
     * Get all ponderaciones (rating scale definitions)
     * returns list of ponderaciones with id, puntuacion, and descripcion
     */
    public List<Map<String, Object>> getPonderaciones() {
        return pedidoRepository.getPonderaciones();
    }
}
