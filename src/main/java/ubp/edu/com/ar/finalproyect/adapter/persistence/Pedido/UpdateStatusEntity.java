package ubp.edu.com.ar.finalproyect.adapter.persistence.Pedido;

public class UpdateStatusEntity {
    private String mensaje;

    public UpdateStatusEntity() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "UpdateStatusEntity{" +
                "mensaje='" + mensaje + '\'' +
                '}';
    }
}
