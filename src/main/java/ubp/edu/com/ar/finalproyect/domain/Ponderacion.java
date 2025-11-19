package ubp.edu.com.ar.finalproyect.domain;

public class Ponderacion {
    private int id;
    private int puntuacion;
    private String descripcion;

    public Ponderacion() {
    }

    public Ponderacion(int id, int puntuacion, String descripcion) {
        this.id = id;
        this.puntuacion = puntuacion;
        this.descripcion = descripcion;
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
        return "Ponderacion{" +
                "id=" + id +
                ", puntuacion=" + puntuacion +
                '}';
    }
}
