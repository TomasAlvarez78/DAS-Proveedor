package ubp.edu.com.ar.finalproyect.adapter.persistence.Producto;

public class ProductoEntity {
    private int codigoBarra;
    private String nombre;
    private String imagen;
    private int stock;
    private float precio;
    private boolean available;

    public ProductoEntity() {
    }

    public ProductoEntity(int codigoBarra, String nombre, float precio) {
        this.codigoBarra = codigoBarra;
        this.nombre = nombre;
        this.precio = precio;
    }

    public ProductoEntity(int codigoBarra, String nombre, boolean available) {
        this.codigoBarra = codigoBarra;
        this.nombre = nombre;
        this.available = available;
    }

    public ProductoEntity(int codigoBarra, String nombre, String imagen, int stock, float precio) {
        this.codigoBarra = codigoBarra;
        this.nombre = nombre;
        this.imagen = imagen;
        this.stock = stock;
        this.precio = precio;
    }

    public int getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(int codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "ProductoEntity{" +
                "codigoBarra = " + codigoBarra +
                ", nombre = '" + nombre + '\'' +
                ", stock = " + stock +
                ", precio = " + precio +
                '}';
    }
}
