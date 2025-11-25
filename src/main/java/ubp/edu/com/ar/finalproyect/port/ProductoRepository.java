package ubp.edu.com.ar.finalproyect.port;

import ubp.edu.com.ar.finalproyect.domain.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository {
    /**
     * Get all products with current prices
     * @return List of all products
     */
    List<Producto> getProductos();
    /**
     * Get a product using codigoBarra - id
     * @return A single product
     */
    Producto getProducto(int codigoBarra);
}
