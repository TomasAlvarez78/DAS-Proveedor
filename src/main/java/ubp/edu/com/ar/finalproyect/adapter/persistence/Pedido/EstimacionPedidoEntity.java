package ubp.edu.com.ar.finalproyect.adapter.persistence.Pedido;

import java.time.LocalDateTime;

public class EstimacionPedidoEntity {
    private LocalDateTime fechaEstimada;
    private float precioEstimadoTotal;
    private String productosJson;

    public EstimacionPedidoEntity() {
    }

    public LocalDateTime getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(LocalDateTime fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    public float getPrecioEstimadoTotal() {
        return precioEstimadoTotal;
    }

    public void setPrecioEstimadoTotal(float precioEstimadoTotal) {
        this.precioEstimadoTotal = precioEstimadoTotal;
    }

    public String getProductosJson() {
        return productosJson;
    }

    public void setProductosJson(String productosJson) {
        this.productosJson = productosJson;
    }

    @Override
    public String toString() {
        return "EstimacionPedidoEntity{" +
                "fechaEstimada=" + fechaEstimada +
                ", precioEstimadoTotal=" + precioEstimadoTotal +
                ", productosJson='" + productosJson + '\'' +
                '}';
    }
}
