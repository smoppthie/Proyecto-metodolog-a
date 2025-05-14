package pedidos;

import clientes.Cliente;
import productos.Producto;
import pagos.MetodoDePago;
import java.util.List;

public class Pedido {
    private String id;
    private Cliente cliente;
    private List<Producto> productos;
    private MetodoDePago metodoPago;
    private String tipoPedido;
    private String estado;

    // Constructor
    public Pedido(String id, Cliente cliente, List<Producto> productos, MetodoDePago metodoPago, String tipoPedido) {
        this.id = id;
        this.cliente = cliente;
        this.productos = productos;
        this.metodoPago = metodoPago;
        this.tipoPedido = tipoPedido;
        this.estado = "pendiente";
    }

    public void pagar() {
        metodoPago.procesarPago(calcularTotal());
        estado = "pagado";
    }

    public double calcularTotal() {
        double total = 0;
        for (Producto producto : productos) {
            total += producto.getPrecio();
        }
        return total - obtenerDescuento();
    }


    public double obtenerDescuento() {
        // Obtener descuento como un valor numérico
        double descuento = cliente.obtenerDescuento();

        if (descuento == 0.05) {
            return 0.05;  // 5% descuento
        } else if (descuento == 0.10) {
            return 0.10;  // 10% descuento
        } else if (descuento == 0.15) {
            return 0.15;  // 15% descuento + envío gratis
        } else {
            return 0;  // Sin descuento
        }
    }




    public String getEstado() {
        return estado;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }
}
