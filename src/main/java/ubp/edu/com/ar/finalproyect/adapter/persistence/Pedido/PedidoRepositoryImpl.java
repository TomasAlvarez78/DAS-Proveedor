package ubp.edu.com.ar.finalproyect.adapter.persistence.Pedido;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import ubp.edu.com.ar.finalproyect.adapter.persistence.Producto.ProductoEntity;
import ubp.edu.com.ar.finalproyect.port.PedidoRepository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PedidoRepositoryImpl implements PedidoRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public PedidoRepositoryImpl(JdbcTemplate jdbcTemplate,ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, Object> estimarPedido(String productosJson) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_estimar_pedido")
                    .declareParameters(
                            new SqlParameter("productosJson", Types.NVARCHAR)
                    )
                    .returningResultSet("resultSet", BeanPropertyRowMapper.newInstance(EstimacionPedidoEntity.class));

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("productosJson", productosJson);

            Map<String, Object> result = jdbcCall.execute(in);

            @SuppressWarnings("unchecked")
            List<EstimacionPedidoEntity> resultSet = (List<EstimacionPedidoEntity>) result.get("resultSet");

            if (resultSet != null && !resultSet.isEmpty()) {
                return toMapEstimacion(resultSet.get(0));
            }

            return new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException("Error estimating pedido", e);
        }
    }

    @Override
    public Map<String, Object> asignarPedido(int idCliente, String productosJson) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_asignar_pedido")
                    .declareParameters(
                            new SqlParameter("idCliente", Types.SMALLINT),
                            new SqlParameter("productosJson", Types.NVARCHAR)
                    )
                    .returningResultSet("resultSet", BeanPropertyRowMapper.newInstance(AsignacionPedidoEntity.class));

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("idCliente", idCliente)
                    .addValue("productosJson", productosJson);

            Map<String, Object> result = jdbcCall.execute(in);

            @SuppressWarnings("unchecked")
            List<AsignacionPedidoEntity> resultSet = (List<AsignacionPedidoEntity>) result.get("resultSet");

            if (resultSet != null && !resultSet.isEmpty()) {
                return toMapAsignacion(resultSet.get(0));
            }

            return new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException("Error assigning pedido", e);
        }
    }

    @Override
    public Map<String, Object> consultarEstado(long idPedido) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_consultar_estado")
                    .declareParameters(
                            new SqlParameter("idPedido", Types.BIGINT)
                    )
                    .returningResultSet("resultSet", BeanPropertyRowMapper.newInstance(EstadoPedidoEntity.class));

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("idPedido", idPedido);

            Map<String, Object> result = jdbcCall.execute(in);

            @SuppressWarnings("unchecked")
            List<EstadoPedidoEntity> resultSet = (List<EstadoPedidoEntity>) result.get("resultSet");

            if (resultSet != null && !resultSet.isEmpty()) {
                return toMapEstado(resultSet.get(0));
            }

            return new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException("Error consulting estado", e);
        }
    }

    @Override
    public Map<String, Object> cancelarPedido(long idPedido) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_cancelar_pedido")
                    .declareParameters(
                            new SqlParameter("idPedido", Types.BIGINT)
                    )
                    .returningResultSet("resultSet", BeanPropertyRowMapper.newInstance(CancelacionPedidoEntity.class));

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("idPedido", idPedido);

            Map<String, Object> result = jdbcCall.execute(in);

            @SuppressWarnings("unchecked")
            List<CancelacionPedidoEntity> resultSet = (List<CancelacionPedidoEntity>) result.get("resultSet");

            if (resultSet != null && !resultSet.isEmpty()) {
                return toMapCancelacion(resultSet.get(0));
            }

            return new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException("Error canceling pedido", e);
        }
    }

    @Override
    public Map<String, Object> updatePedidoStatus(long idPedido, int estadoId) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_update_pedido_status")
                    .declareParameters(
                            new SqlParameter("idPedido", Types.BIGINT),
                            new SqlParameter("estadoId", Types.SMALLINT)
                    )
                    .returningResultSet("resultSet", BeanPropertyRowMapper.newInstance(UpdateStatusEntity.class));

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("idPedido", idPedido)
                    .addValue("estadoId", estadoId);

            Map<String, Object> result = jdbcCall.execute(in);

            @SuppressWarnings("unchecked")
            List<UpdateStatusEntity> resultSet = (List<UpdateStatusEntity>) result.get("resultSet");

            if (resultSet != null && !resultSet.isEmpty()) {
                return toMapUpdateStatus(resultSet.get(0));
            }

            return new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException("Error updating pedido status", e);
        }
    }

    // Helper: Entity → Map
    private Map<String, Object> toMapEstimacion(EstimacionPedidoEntity entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("fechaEstimada", entity.getFechaEstimada());
        map.put("precioEstimadoTotal", entity.getPrecioEstimadoTotal());
//        map.put("productosJson", entity.getProductosJson());

        // Jsoniniation
        try {
            if (entity.getProductosJson() != null) {
                // We use TypeReference because we are parsing a List, not a single object
                List<EstimacionProductoSubEntity> productos = objectMapper.readValue(
                        entity.getProductosJson(),
                        new TypeReference<List<EstimacionProductoSubEntity>>() {}
                );

                map.put("productos", productos);
            } else {
                map.put("productos", List.of());
            }
        } catch (JsonProcessingException e) {
            // Log error and return empty list or rethrow depending on your needs
            System.err.println("Error parsing products JSON: " + e.getMessage());
            map.put("productos", List.of());
        }

        return map;
    }

    // Helper: Entity → Map
    private Map<String, Object> toMapAsignacion(AsignacionPedidoEntity entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("idPedido", entity.getIdPedido());
        map.put("estadoPedido", entity.getEstadoPedido());
        map.put("fechaEstimada", entity.getFechaEstimada());
        map.put("precioTotal", entity.getPrecioTotal());
        return map;
    }

    // Helper: Entity → Map
    private Map<String, Object> toMapEstado(EstadoPedidoEntity entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("idPedido", entity.getIdPedido());
        map.put("estado", entity.getEstado());
        if (entity.getDescripcion() != null) {
            map.put("descripcion", entity.getDescripcion());
        }
        return map;
    }

    // Helper: Entity → Map
    private Map<String, Object> toMapCancelacion(CancelacionPedidoEntity entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("idPedido", entity.getIdPedido());
        map.put("estado", entity.getEstado());
        if (entity.getDescripcion() != null) {
            map.put("descripcion", entity.getDescripcion());
        }
        return map;
    }

    // Helper: Entity → Map
    private Map<String, Object> toMapUpdateStatus(UpdateStatusEntity entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("mensaje", entity.getMensaje());
        return map;
    }

    @Override
    public Map<String, Object> puntuarPedido(long idPedido, int puntuacion) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_puntuar_pedido")
                    .declareParameters(
                            new SqlParameter("idPedido", Types.BIGINT),
                            new SqlParameter("puntuacion", Types.TINYINT)
                    )
                    .returningResultSet("resultSet", BeanPropertyRowMapper.newInstance(PuntuacionPedidoEntity.class));

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("idPedido", idPedido)
                    .addValue("puntuacion", puntuacion);

            Map<String, Object> result = jdbcCall.execute(in);

            @SuppressWarnings("unchecked")
            List<PuntuacionPedidoEntity> resultSet = (List<PuntuacionPedidoEntity>) result.get("resultSet");

            if (resultSet != null && !resultSet.isEmpty()) {
                return toMapPuntuacion(resultSet.get(0));
            }

            return new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException("Error rating pedido", e);
        }
    }

    // Helper: Entity → Map
    private Map<String, Object> toMapPuntuacion(PuntuacionPedidoEntity entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("idPedido", String.valueOf(entity.getIdPedido()));
        map.put("descripcion", entity.getDescripcion());
        return map;
    }

    @Override
    public List<Map<String, Object>> getPonderaciones() {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_get_ponderaciones")
                    .returningResultSet("resultSet", BeanPropertyRowMapper.newInstance(PonderacionEntity.class));

            Map<String, Object> result = jdbcCall.execute();

            @SuppressWarnings("unchecked")
            List<PonderacionEntity> resultSet = (List<PonderacionEntity>) result.get("resultSet");

            if (resultSet != null && !resultSet.isEmpty()) {
                List<Map<String, Object>> ponderaciones = new ArrayList<>();
                for (PonderacionEntity entity : resultSet) {
                    ponderaciones.add(toMapPonderacion(entity));
                }
                return ponderaciones;
            }

            return new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Error getting ponderaciones", e);
        }
    }

    // Helper: Entity → Map
    private Map<String, Object> toMapPonderacion(PonderacionEntity entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", entity.getId());
        map.put("puntuacion", entity.getPuntuacion());
        if (entity.getDescripcion() != null) {
            map.put("descripcion", entity.getDescripcion());
        }
        return map;
    }
}
