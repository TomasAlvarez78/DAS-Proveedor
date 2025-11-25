package ubp.edu.com.ar.finalproyect.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ubp.edu.com.ar.finalproyect.domain.Clientes;
import ubp.edu.com.ar.finalproyect.port.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticate client using clientId and API key
     * ex clientId Client identifier (ex. "supercaracol")
     * ex apiKey text API key from request (ex. "P3L1C4N0")
     * returns Client object if authenticated else null
     */
    public Clientes authenticateClient(String clientId, String apiKey) {

        Clientes cliente = clienteRepository.getClienteByIdentifier(clientId);

        if (cliente == null) {
            return null;
        }

        System.out.println(apiKey);
        System.out.println(cliente.getApiKey());

        if (passwordEncoder.matches(apiKey, cliente.getApiKey())) {
            System.out.println("Correct api key");
            return cliente;
        }
        System.out.println("Incorrect api key");

        return null;
    }

    /**
     * Create new client with BCrypt-hashed API key
     * returns Created Clientes object or null if creation failed
     */
    public Clientes createCliente(String clientIdentifier, String nombre, String descripcion, String plainApiKey, String servicio) {
        // Hash the plain-text API key using BCrypt
        String hashedApiKey = passwordEncoder.encode(plainApiKey);

        // Create client with hashed API key
        return clienteRepository.createCliente(clientIdentifier, nombre, descripcion, hashedApiKey, servicio);
    }
}
