package ubp.edu.com.ar.finalproyect.adapter.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubp.edu.com.ar.finalproyect.domain.Clientes;
import ubp.edu.com.ar.finalproyect.service.ClienteService;
import ubp.edu.com.ar.finalproyect.service.PedidoService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/proveedor")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final ObjectMapper objectMapper;

    @Value("${app.admin.user}")
    private String adminUser;

    public PedidoController(PedidoService pedidoService, ClienteService clienteService, ObjectMapper objectMapper) {
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
        this.objectMapper = objectMapper;
    }

    /**
     * Estimate if order can be created with current stock / products
     * POST /api/proveedor/estimarPedido?clientId={clientId}&apikey={apikey}
     */
    @PostMapping("/estimarPedido")
    public ResponseEntity<Map<String, Object>> estimarPedido(
            @RequestParam("clientId") String clientId,
            @RequestParam("apikey") String apiKey,
            @RequestBody Map<String, Object> request) {

        try {
            Clientes cliente = clienteService.authenticateClient(clientId, apiKey);

            if (cliente == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", "KO");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }

            System.out.println("Client found: " + cliente.getNombre());

            @SuppressWarnings("unchecked")
            Map<String, Object> pedido = (Map<String, Object>) request.get("Pedido");

            if (pedido == null || !pedido.containsKey("productos")) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Invalid request format");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            String productosJson = objectMapper.writeValueAsString(pedido.get("productos"));

            Map<String, Object> estimacion = pedidoService.estimarPedido(productosJson);

            Map<String, Object> response = new HashMap<>();
            response.put("Pedido", estimacion);

            return ResponseEntity.ok(response);

        } catch (JsonProcessingException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error processing JSON");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Register order
     * POST /api/proveedor/asignarPedido?clientId={clientId}&apikey={apikey}
     */
    @PostMapping("/asignarPedido")
    public ResponseEntity<Map<String, Object>> asignarPedido(
            @RequestParam("clientId") String clientId,
            @RequestParam("apikey") String apiKey,
            @RequestBody Map<String, Object> request) {

        try {
            Clientes cliente = clienteService.authenticateClient(clientId, apiKey);

            if (cliente == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", "KO");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> pedido = (Map<String, Object>) request.get("Pedido");

            if (pedido == null || !pedido.containsKey("productos")) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Invalid request format");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            String productosJson = objectMapper.writeValueAsString(pedido.get("productos"));

            Map<String, Object> asignacion = pedidoService.asignarPedido(cliente.getId(), productosJson);

            Map<String, Object> response = new HashMap<>();
            response.put("Pedido", asignacion);

            return ResponseEntity.ok(response);

        } catch (JsonProcessingException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error processing JSON");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Get order status
     * GET /api/proveedor/consultarEstado?clientId={clientId}&apikey={apikey}&idPedido={idPedido}
     */
    @GetMapping("/consultarEstado")
    public ResponseEntity<Map<String, Object>> consultarEstado(
            @RequestParam("clientId") String clientId,
            @RequestParam("apikey") String apiKey,
            @RequestParam("idPedido") Long idPedido) {

        try {
            Clientes cliente = clienteService.authenticateClient(clientId, apiKey);

            if (cliente == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", "KO");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }

            Map<String, Object> estado = pedidoService.consultarEstado(idPedido);

            return ResponseEntity.ok(estado);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Cancel order
     * GET /api/proveedor/cancelarPedido?clientId={clientId}&apikey={apikey}&idPedido={idPedido}
     */
    @GetMapping("/cancelarPedido")
    public ResponseEntity<Map<String, Object>> cancelarPedido(
            @RequestParam("clientId") String clientId,
            @RequestParam("apikey") String apiKey,
            @RequestParam("idPedido") Long idPedido) {

        try {
            Clientes cliente = clienteService.authenticateClient(clientId, apiKey);

            if (cliente == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", "KO");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }

            Map<String, Object> cancelacion = pedidoService.cancelarPedido(idPedido);

            return ResponseEntity.ok(cancelacion);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Update order status
     * PUT /api/proveedor/updatePedidoStatus?clientId={clientId}&apikey={apikey}&idPedido={idPedido}&estadoId={estadoId}
     */
    @PutMapping("/updatePedidoStatus")
    public ResponseEntity<Map<String, Object>> updatePedidoStatus(
            @RequestParam("clientId") String clientId,
            @RequestParam("apikey") String apiKey,
            @RequestParam("idPedido") Long idPedido,
            @RequestParam("estadoId") Integer estadoId) {

        try {
            Clientes cliente = clienteService.authenticateClient(clientId, apiKey);

            if (cliente == null || !cliente.getClientIdentifier().equals(adminUser)) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", "KO");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }


            Map<String, Object> resultado = pedidoService.updatePedidoStatus(idPedido, estadoId);

            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Rate an order
     * GET /api/proveedor/puntuarPedido?clientId={clientId}&apikey={apikey}&idPedido={idPedido}&puntuacion={puntuacion}
     */
    @GetMapping("/puntuarPedido")
    public ResponseEntity<Map<String, Object>> puntuarPedido(
            @RequestParam("clientId") String clientId,
            @RequestParam("apikey") String apiKey,
            @RequestParam("idPedido") Long idPedido,
            @RequestParam("puntuacion") Integer puntuacion) {

        try {
            Clientes cliente = clienteService.authenticateClient(clientId, apiKey);

            if (cliente == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", "KO");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }

            Map<String, Object> puntuacionResult = pedidoService.puntuarPedido(idPedido, puntuacion);

            return ResponseEntity.ok(puntuacionResult);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
