package pedidos;

import productos.Producto;

import java.util.List;

public class Factura {
    private Pedido pedido;

    public Factura(Pedido pedido) {
        this.pedido = pedido;
    }

    public String generarDetalle() {
        StringBuilder sb = new StringBuilder();
        sb.append("Factura para pedido ID: ").append(pedido.getId()).append("\n");
        sb.append("Cliente: ").append(pedido.getCliente().getNombre()).append("\n");
        sb.append("Productos:\n");
        List<Producto> productos = pedido.getProductos();
        for (Producto p : productos) {
            sb.append(" - ").append(p.getNombre())
              .append(" | Código: ").append(p.getCodigo())
              .append(" | Precio: $").append(p.getPrecio()).append("\n");
        }
        sb.append("Total: $").append(pedido.calcularTotal()).append("\n");
        sb.append("Estado de pago: ").append(pedido.getEstado()).append("\n");
        return sb.toString();
    }
}

