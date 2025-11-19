package ubp.edu.com.ar.finalproyect.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    private long id;
    private int estado;
    private int puntuacion;
    private LocalDateTime fechaEstimada;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaRegistro;
    private int cliente;
    private List<PedidoProducto> productos;

    public Pedido() {
    }

    public Pedido(long id, int estado, int puntuacion, LocalDateTime fechaEstimada,
                  LocalDateTime fechaEntrega, LocalDateTime fechaRegistro, int cliente) {
        this.id = id;
        this.estado = estado;
        this.puntuacion = puntuacion;
        this.fechaEstimada = fechaEstimada;
        this.fechaEntrega = fechaEntrega;
        this.fechaRegistro = fechaRegistro;
        this.cliente = cliente;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public LocalDateTime getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(LocalDateTime fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public List<PedidoProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<PedidoProducto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", estado=" + estado +
                ", cliente=" + cliente +
                ", fechaEstimada=" + fechaEstimada +
                '}';
    }
}
