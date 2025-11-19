package ubp.edu.com.ar.finalproyect.domain;

public class EstadoPedido {
    private int id;
    private String nombre;
    private String descripcion;

    public EstadoPedido() {
    }

    public EstadoPedido(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "EstadoPedido{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
