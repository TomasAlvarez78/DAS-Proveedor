package ubp.edu.com.ar.finalproyect.adapter.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import ubp.edu.com.ar.finalproyect.adapter.persistence.entity.ClientesEntity;
import ubp.edu.com.ar.finalproyect.adapter.persistence.entity.ProductoEntity;
import ubp.edu.com.ar.finalproyect.domain.Clientes;
import ubp.edu.com.ar.finalproyect.domain.Producto;
import ubp.edu.com.ar.finalproyect.port.ProveedorRepository;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JDBC implementation of ProveedorRepository
 * Uses stored procedures to interact with the database via SimpleJdbcCall
 */
@Repository
public class ProveedorRepositoryImpl implements ProveedorRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProveedorRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Health check - validate database connection
     */
    @Override
    public String health() {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_health");
            Map<String, Object> result = jdbcCall.execute();
            return result != null && result.get("status") != null ?
                    result.get("status").toString() : "OK";
        } catch (Exception e) {
            throw new RuntimeException("Health check failed", e);
        }
    }

    /**
     * Get all products with current prices
     */
    @Override
    public List<Producto> getProductos() {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_get_productos")
                    .returningResultSet("resultSet", BeanPropertyRowMapper.newInstance(ProductoEntity.class));

            Map<String, Object> result = jdbcCall.execute();

            @SuppressWarnings("unchecked")
            List<ProductoEntity> productos = (List<ProductoEntity>) result.get("resultSet");

            if (productos != null) {
                return productos.stream()
                        .map(this::toDomainProducto)
                        .toList();
            }

            return List.of();
        } catch (Exception e) {
            throw new RuntimeException("Error getting productos", e);
        }
    }

    /**
     * Get client by API key for authentication
     */
    @Override
    public Clientes getClienteByApiKey(String apiKey) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_get_cliente_by_apikey")
                    .declareParameters(
                            new SqlParameter("apiKey", Types.NVARCHAR)
                    )
                    .returningResultSet("resultSet", BeanPropertyRowMapper.newInstance(ClientesEntity.class));

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("apiKey", apiKey);

            Map<String, Object> result = jdbcCall.execute(in);

            @SuppressWarnings("unchecked")
            List<ClientesEntity> clientes = (List<ClientesEntity>) result.get("resultSet");

            if (clientes != null && !clientes.isEmpty()) {
                return toDomainClientes(clientes.get(0));
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error getting cliente by API key", e);
        }
    }

    /**
     * Estimate if an order can be executed
     */
    @Override
    public Map<String, Object> estimarPedido(int idCliente, String productosJson) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_estimar_pedido")
                    .declareParameters(
                            new SqlParameter("idCliente", Types.SMALLINT),
                            new SqlParameter("productosJson", Types.NVARCHAR)
                    );

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("idCliente", idCliente)
                    .addValue("productosJson", productosJson);

            Map<String, Object> result = jdbcCall.execute(in);

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

            return resultSet != null && !resultSet.isEmpty() ? resultSet.get(0) : new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException("Error estimating pedido", e);
        }
    }

    /**
     * Confirm and register an order
     */
    @Override
    public Map<String, Object> asignarPedido(int idCliente, String productosJson) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_asignar_pedido")
                    .declareParameters(
                            new SqlParameter("idCliente", Types.SMALLINT),
                            new SqlParameter("productosJson", Types.NVARCHAR)
                    );

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("idCliente", idCliente)
                    .addValue("productosJson", productosJson);

            Map<String, Object> result = jdbcCall.execute(in);

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

            return resultSet != null && !resultSet.isEmpty() ? resultSet.get(0) : new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException("Error assigning pedido", e);
        }
    }

    /**
     * Get order status
     */
    @Override
    public Map<String, Object> consultarEstado(long idPedido) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_consultar_estado")
                    .declareParameters(
                            new SqlParameter("idPedido", Types.BIGINT)
                    );

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("idPedido", idPedido);

            Map<String, Object> result = jdbcCall.execute(in);

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

            return resultSet != null && !resultSet.isEmpty() ? resultSet.get(0) : new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException("Error consulting estado", e);
        }
    }

    /**
     * Cancel an order
     */
    @Override
    public Map<String, Object> cancelarPedido(long idPedido) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_cancelar_pedido")
                    .declareParameters(
                            new SqlParameter("idPedido", Types.BIGINT)
                    );

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("idPedido", idPedido);

            Map<String, Object> result = jdbcCall.execute(in);

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

            return resultSet != null && !resultSet.isEmpty() ? resultSet.get(0) : new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException("Error canceling pedido", e);
        }
    }

    // Helper: Entity → Domain
    private Producto toDomainProducto(ProductoEntity entity) {
        return new Producto(
                entity.getId(),
                entity.getNombre(),
                entity.getImagen(),
                entity.getStock(),
                entity.getPrecio()
        );
    }

    // Helper: Entity → Domain
    private Clientes toDomainClientes(ClientesEntity entity) {
        return new Clientes(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getApiKey(),
                entity.getServicio()
        );
    }
}
