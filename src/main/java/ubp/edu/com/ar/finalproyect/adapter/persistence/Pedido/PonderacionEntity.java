package ubp.edu.com.ar.finalproyect.adapter.persistence.Pedido;

public class PonderacionEntity {
    private int id;
    private int puntuacion;
    private String descripcion;

    public PonderacionEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "PonderacionEntity{" +
                "id=" + id +
                ", puntuacion=" + puntuacion +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
