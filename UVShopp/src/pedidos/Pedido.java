package pedidos;

import clientes.Cliente;
import productos.Producto;
import pagos.MetodoDePago;

import java.util.List;

public class Pedido {
    private String id;
    private Cliente cliente;
    private List<Producto> productos;
    private TipoPedido tipoPedido;
    private MetodoDePago metodoPago;
    private String estado; // pendiente, pagado, en preparación, enviado, entregado, cancelado

    public Pedido(String id, Cliente cliente, List<Producto> productos, TipoPedido tipoPedido, MetodoDePago metodoPago) {
        this.id = id;
        this.cliente = cliente;
        this.productos = productos;
        this.tipoPedido = tipoPedido;
        this.metodoPago = metodoPago;
        this.estado = "pendiente";
    }

    public double calcularSubtotal() {
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }

    public double calcularTotal() {
        double subtotal = calcularSubtotal();
        double costoExtra = tipoPedido.calcularCostoExtra(cliente);
        double impuestos = tipoPedido.calcularImpuestos(subtotal);
        double descuento = subtotal * cliente.getDescuento();
        return subtotal + costoExtra + impuestos - descuento;
    }

    public void pagar() {
        if ("pagado".equalsIgnoreCase(estado)) {
            throw new IllegalStateException("El pedido ya está pagado");
        }
        metodoPago.procesarPago(calcularTotal());
        estado = "pagado";
    }

    public void prepararEnvio() {
        if (!"pagado".equalsIgnoreCase(estado)) throw new IllegalStateException("Pedido no pagado");
        estado = "en preparación";
    }

    public void enviar() {
        if (!"en preparación".equalsIgnoreCase(estado)) throw new IllegalStateException("Pedido no está en preparación");
        estado = "enviado";
    }

    public void entregar() {
        if (!"enviado".equalsIgnoreCase(estado)) throw new IllegalStateException("Pedido no ha sido enviado");
        estado = "entregado";
    }

    public void cancelar() {
        if ("pagado".equalsIgnoreCase(estado) || "en preparación".equalsIgnoreCase(estado)) {
            estado = "cancelado";
        } else {
            throw new IllegalStateException("No se puede cancelar un pedido en estado: " + estado);
        }
    }

    public void recuperar() {
        if (!"cancelado".equalsIgnoreCase(estado)) {
            throw new IllegalStateException("Solo se pueden recuperar pedidos cancelados.");
        }
        estado = "pagado";
    }

    public void setTipoPedido(TipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public void setMetodoPago(MetodoDePago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<Producto> getProductos() { return productos; }
    public String getEstado() { return estado; }
    public TipoPedido getTipoPedido() { return tipoPedido; }
}
