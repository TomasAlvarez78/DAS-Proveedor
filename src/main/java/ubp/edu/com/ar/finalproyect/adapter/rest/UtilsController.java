package ubp.edu.com.ar.finalproyect.adapter.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubp.edu.com.ar.finalproyect.domain.Clientes;
import ubp.edu.com.ar.finalproyect.service.ClienteService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UtilsController {

    private final ClienteService clienteService;

    public UtilsController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Health check endpoint
    // GET /api/health?clientId={clientId}&apikey={apikey}
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health(
            @RequestParam("clientId") String clientId,
            @RequestParam("apikey") String apiKey) {
        try {
            Clientes cliente = clienteService.authenticateClient(clientId, apiKey);

            Map<String, Object> response = new HashMap<>();

            if (cliente == null) {
                response.put("status", "KO");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            response.put("status", "OK");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "KO");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
