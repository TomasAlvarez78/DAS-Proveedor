package ubp.edu.com.ar.finalproyect.port;

import ubp.edu.com.ar.finalproyect.domain.Clientes;

public interface ClienteRepository {
    /**
     * Get client by client identifier for authentication
     * @param clientIdentifier The client identifier (e.g., "supercaracol")
     * @return Clientes object if found, null otherwise
     */
    Clientes getClienteByIdentifier(String clientIdentifier);

    /**
     * Create new client with hashed API key
     * @param clientIdentifier Unique client identifier
     * @param nombre Client name
     * @param descripcion Client description
     * @param hashedApiKey BCrypt-hashed API key
     * @param servicio Service type
     * @return Created Clientes object or null if failed
     */
    Clientes createCliente(String clientIdentifier, String nombre, String descripcion, String hashedApiKey, String servicio);
}
