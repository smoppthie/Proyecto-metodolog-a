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
        this.estado = "pendiente";  // Estado inicial del pedido
    }

    // Método para procesar el pago
    public void pagar() {
        metodoPago.procesarPago(calcularTotal());  // Llamada al método de pago
        estado = "pagado";  // Cambiar el estado del pedido a "pagado"
    }

    // Calcular el total del pedido
    public double calcularTotal() {
        double total = 0;
        for (Producto producto : productos) {
            total += producto.getPrecio();  // Sumar el precio de los productos
        }
        return total;
    }

    // Métodos de acceso a los campos
    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getEstado() {
        return estado;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }
}
