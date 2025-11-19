package ubp.edu.com.ar.finalproyect.adapter.persistence.entity;

public class ClientesEntity {
    private int id;
    private String nombre;
    private String descripcion;
    private String apiKey;
    private String servicio;

    public ClientesEntity() {
    }

    public ClientesEntity(int id, String nombre, String descripcion, String apiKey, String servicio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.apiKey = apiKey;
        this.servicio = servicio;
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

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    @Override
    public String toString() {
        return "ClientesEntity{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", servicio='" + servicio + '\'' +
                '}';
    }
}
