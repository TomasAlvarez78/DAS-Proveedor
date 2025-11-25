package ubp.edu.com.ar.finalproyect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubp.edu.com.ar.finalproyect.domain.Producto;
import ubp.edu.com.ar.finalproyect.exception.ProductoNotFoundException;
import ubp.edu.com.ar.finalproyect.port.ProductoRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductoService {

    private final  ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) { this.productoRepository = productoRepository; }

    /**
     * Get products and currentPrices
     * returns product's data
     */
    public List<Producto> getProductos() { return productoRepository.getProductos(); }

    /**
     * Get product and currentPrice of singular product
     * returns product's data
     */
    public Producto getProductoById(int codigoBarra) {

//        Producto product = new Producto();
//        product = repository.findByBarCode(barCode)
//            .orElseThrow(() -> new ProductoNotFoundException(barCode));

        return productoRepository.getProducto(codigoBarra);
    }
}
