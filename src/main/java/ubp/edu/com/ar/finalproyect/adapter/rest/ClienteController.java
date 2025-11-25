package ubp.edu.com.ar.finalproyect.adapter.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubp.edu.com.ar.finalproyect.domain.Clientes;
import ubp.edu.com.ar.finalproyect.service.ClienteService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

     // Get client by client identifier and API key authentication
     // GET /api/clientes?clientId={clientId}&apikey={apikey}
    @GetMapping
    public ResponseEntity<Clientes> getCliente(
            @RequestParam("clientId") String clientId,
            @RequestParam("apikey") String apiKey) {

        Clientes cliente = clienteService.authenticateClient(clientId, apiKey);

        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(cliente);
    }

    // Create new client after hashing API key
    // POST /api/clientes/register
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerCliente(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            String clientIdentifier = request.get("clientIdentifier");
            String nombre = request.get("nombre");
            String descripcion = request.get("descripcion");
            String plainApiKey = request.get("apiKey");
            String servicio = request.get("servicio");

            if (clientIdentifier == null || nombre == null || plainApiKey == null || servicio == null) {
                response.put("status", "error");
                response.put("message", "Missing required fields: clientIdentifier, nombre, apiKey, servicio");
                return ResponseEntity.badRequest().body(response);
            }

            Clientes newCliente = clienteService.createCliente(clientIdentifier, nombre, descripcion, plainApiKey, servicio);

            if (newCliente == null) {
                response.put("status", "error");
                response.put("message", "Client already exists or creation failed");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            response.put("status", "success");
            response.put("message", "Client created successfully");
            response.put("clientId", newCliente.getClientIdentifier());
            response.put("nombre", newCliente.getNombre());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error creating client: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
