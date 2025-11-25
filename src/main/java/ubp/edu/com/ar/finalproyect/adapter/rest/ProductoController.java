package ubp.edu.com.ar.finalproyect.adapter.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubp.edu.com.ar.finalproyect.domain.Producto;
import ubp.edu.com.ar.finalproyect.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) { this.productoService = productoService; }

    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos(){
//        return productoService.getProductos().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        return ResponseEntity.ok(productoService.getProductos());
    }

    @GetMapping("/{codigoBarra}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Integer codigoBarra){
        Producto producto = productoService.getProductoById(codigoBarra);
        return ResponseEntity.ok(producto);
//        return ResponseEntity.ok(productoService.getProductoById(id));
    }
}
