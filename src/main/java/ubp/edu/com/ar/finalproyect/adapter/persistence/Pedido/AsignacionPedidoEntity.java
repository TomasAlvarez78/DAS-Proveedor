package ubp.edu.com.ar.finalproyect.adapter.persistence.Pedido;

import java.time.LocalDateTime;

public class AsignacionPedidoEntity {
    private long idPedido;
    private String estadoPedido;
    private LocalDateTime fechaEstimada;
    private float precioTotal;

    public AsignacionPedidoEntity() {
    }

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public LocalDateTime getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(LocalDateTime fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    @Override
    public String toString() {
        return "AsignacionPedidoEntity{" +
                "idPedido=" + idPedido +
                ", estadoPedido='" + estadoPedido + '\'' +
                ", fechaEstimada=" + fechaEstimada +
                ", precioTotal=" + precioTotal +
                '}';
    }
}
