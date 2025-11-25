package ubp.edu.com.ar.finalproyect.adapter.persistence.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import ubp.edu.com.ar.finalproyect.domain.Clientes;
import ubp.edu.com.ar.finalproyect.port.ClienteRepository;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClienteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Clientes getClienteByIdentifier(String clientIdentifier) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_get_cliente_by_identifier")
                    .declareParameters(
                            new SqlParameter("clientIdentifier", Types.NVARCHAR)
                    )
                    .returningResultSet("resultSet", BeanPropertyRowMapper.newInstance(ClienteEntity.class));

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("clientIdentifier", clientIdentifier);

            Map<String, Object> result = jdbcCall.execute(in);

            @SuppressWarnings("unchecked")
            List<ClienteEntity> clientes = (List<ClienteEntity>) result.get("resultSet");

            if (clientes != null && !clientes.isEmpty()) {
                return toDomainCliente(clientes.get(0));
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error getting cliente by identifier", e);
        }
    }

    @Override
    public Clientes createCliente(String clientIdentifier, String nombre, String descripcion, String hashedApiKey, String servicio) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_create_cliente")
                    .declareParameters(
                            new SqlParameter("clientIdentifier", Types.NVARCHAR),
                            new SqlParameter("nombre", Types.NVARCHAR),
                            new SqlParameter("descripcion", Types.NVARCHAR),
                            new SqlParameter("hashedApiKey", Types.NVARCHAR),
                            new SqlParameter("servicio", Types.NVARCHAR)
                    )
                    .returningResultSet("resultSet", BeanPropertyRowMapper.newInstance(ClienteEntity.class));

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("clientIdentifier", clientIdentifier)
                    .addValue("nombre", nombre)
                    .addValue("descripcion", descripcion)
                    .addValue("hashedApiKey", hashedApiKey)
                    .addValue("servicio", servicio);

            Map<String, Object> result = jdbcCall.execute(in);

            @SuppressWarnings("unchecked")
            List<ClienteEntity> clientes = (List<ClienteEntity>) result.get("resultSet");

            if (clientes != null && !clientes.isEmpty() && clientes.get(0).getId() != 0) {
                return toDomainCliente(clientes.get(0));
            }

            return null; // exists or creation failed
        } catch (Exception e) {
            throw new RuntimeException("Error creating cliente", e);
        }
    }

    // Helper: Entity → Domain
    private Clientes toDomainCliente(ClienteEntity entity) {
        return new Clientes(
                entity.getId(),
                entity.getClientIdentifier(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getApiKey(),
                entity.getServicio()
        );
    }
}
