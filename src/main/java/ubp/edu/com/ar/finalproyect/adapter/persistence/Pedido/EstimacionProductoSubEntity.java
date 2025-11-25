package ubp.edu.com.ar.finalproyect.adapter.persistence.Pedido;

public class EstimacionProductoSubEntity {
    private int codigoBarra;
    private String nombre;
    private boolean available;

    public EstimacionProductoSubEntity() {
    }

    public EstimacionProductoSubEntity(int codigoBarra, String nombre, boolean available) {
        this.codigoBarra = codigoBarra;
        this.nombre = nombre;
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(int codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    @Override
    public String toString() {
        return "EstimacionProductoSubEntity{" +
                "codigoBarra=" + codigoBarra +
                ", nombre=" + nombre + '\'' +
                ", available='" + available + '\'' +
                '}';
    }
}
