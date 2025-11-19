package ubp.edu.com.ar.finalproyect.domain;

public class PedidoProducto {
    private int id;
    private int codigoBarra;
    private int cantidad;
    private float precioPedido;
    private long idPedido;

    public PedidoProducto() {
    }

    public PedidoProducto(int id, int codigoBarra, int cantidad, float precioPedido, long idPedido) {
        this.id = id;
        this.codigoBarra = codigoBarra;
        this.cantidad = cantidad;
        this.precioPedido = precioPedido;
        this.idPedido = idPedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(int codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioPedido() {
        return precioPedido;
    }

    public void setPrecioPedido(float precioPedido) {
        this.precioPedido = precioPedido;
    }

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    @Override
    public String toString() {
        return "PedidoProducto{" +
                "id=" + id +
                ", codigoBarra=" + codigoBarra +
                ", cantidad=" + cantidad +
                ", precioPedido=" + precioPedido +
                ", idPedido=" + idPedido +
                '}';
    }
}
