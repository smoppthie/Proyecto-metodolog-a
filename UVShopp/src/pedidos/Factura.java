package pedidos;

import clientes.Cliente;
import productos.Producto;

import java.util.List;

public class Factura {
    private String idFactura;
    private Pedido pedido;
    private Cliente cliente;
    private List<Producto> productos;
    private double subtotal;
    private double descuento;
    private double impuestos;
    private double total;
    private String estadoPago;

    public Factura(String idFactura, Pedido pedido) {
        this.idFactura = idFactura;
        this.pedido = pedido;
        this.cliente = pedido.getCliente();
        this.productos = pedido.getProductos();
        this.subtotal = pedido.calcularSubtotal();
        this.descuento = subtotal * cliente.getDescuento();
        this.impuestos = pedido.getTipoPedido().calcularImpuestos(subtotal);
        this.total = pedido.calcularTotal();
        this.estadoPago = pedido.getEstado();
    }

    public String generarResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Factura ID: ").append(idFactura).append("\n");
        sb.append("Cliente: ").append(cliente.getNombre()).append("\n");
        sb.append("Estado de pago: ").append(estadoPago).append("\n");
        sb.append("Productos:\n");
        for (Producto p : productos) {
            sb.append("- ").append(p.getNombre())
              .append(", Código: ").append(p.getCodigo())
              .append(", Precio: $").append(p.getPrecio()).append("\n");
        }
        sb.append("Subtotal: $").append(String.format("%.2f", subtotal)).append("\n");
        sb.append("Descuento: $").append(String.format("%.2f", descuento)).append("\n");
        sb.append("Impuestos: $").append(String.format("%.2f", impuestos)).append("\n");
        sb.append("Total a pagar: $").append(String.format("%.2f", total)).append("\n");
        return sb.toString();
    }

    // Getters si quieres acceder por separado
    public double getTotal() { return total; }
    public String getEstadoPago() { return estadoPago; }
}

