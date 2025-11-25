package ubp.edu.com.ar.finalproyect.adapter.persistence.Producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import ubp.edu.com.ar.finalproyect.adapter.persistence.Producto.ProductoEntity;
import ubp.edu.com.ar.finalproyect.domain.Producto;
import ubp.edu.com.ar.finalproyect.port.ProductoRepository;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductoRepositoryImpl implements ProductoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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

    // TODO
    @Override
    public Producto getProducto(int codigoBarra) {
        try {
            System.out.println("get producto by codigoBarra: " + codigoBarra);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("sp_get_producto")
                    .declareParameters(
                            new SqlParameter("codigoBarra", Types.INTEGER)
                    )
                    .returningResultSet("resultSet", BeanPropertyRowMapper.newInstance(ProductoEntity.class));

            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("codigoBarra", codigoBarra);

            System.out.println("before executing query");
            Map<String, Object> result = jdbcCall.execute(in);
            System.out.println("after executing query");

            @SuppressWarnings("unchecked")
            List<ProductoEntity> producto = (List<ProductoEntity>) result.get("resultSet");

//            System.out.println("list ======================== " + producto.size());

            if (producto != null && !producto.isEmpty()) {
                return toDomainProducto(producto.get(0));
//                return producto.stream()
//                        .map(this::toDomainProducto)
//                        .toList();
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error getting productos", e);
        }
    }

    // Helper: Entity → Domain
    private Producto toDomainProducto(ProductoEntity entity) {
        return new Producto(
                entity.getCodigoBarra(),
                entity.getNombre(),
                entity.getImagen(),
                entity.getStock(),
                entity.getPrecio()
        );
    }

}
