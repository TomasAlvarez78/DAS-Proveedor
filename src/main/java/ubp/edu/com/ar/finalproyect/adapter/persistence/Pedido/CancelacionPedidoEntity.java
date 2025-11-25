package ubp.edu.com.ar.finalproyect.adapter.persistence.Pedido;

public class CancelacionPedidoEntity {
    private long idPedido;
    private String estado;
    private String descripcion;

    public CancelacionPedidoEntity() {
    }

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "CancelacionPedidoEntity{" +
                "idPedido=" + idPedido +
                ", estado='" + estado + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
