package ubp.edu.com.ar.finalproyect.adapter.persistence.Pedido;

public class PuntuacionPedidoEntity {
    private long idPedido;
    private String descripcion;

    public PuntuacionPedidoEntity() {
    }

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "PuntuacionPedidoEntity{" +
                "idPedido=" + idPedido +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
