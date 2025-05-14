import productos.Producto;
import clientes.Cliente;
import pagos.PagoConTarjeta;
import pagos.MetodoDePago;
import pedidos.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Producto producto1 = new Producto("Producto A", "A001", 100.0, 50);
        Producto producto2 = new Producto("Producto B", "B001", 200.0, 30);

        Cliente cliente1 = new Cliente("Juan Pérez", "juan@correo.com", "Calle 123", "Frecuente");

        List<Producto> productos = new ArrayList<>();
        productos.add(producto1);
        productos.add(producto2);

        String tipoPedidoStr = "Estándar";

        TipoPedido pedidoTipo;
        switch (tipoPedidoStr) {
            case "Estándar":
                pedidoTipo = new PedidoEstandar();
                break;
            case "Exprés":
                pedidoTipo = new PedidoExpres();
                break;
            case "Programado":
                pedidoTipo = new PedidoProgramado(LocalDate.now().plusDays(3));
                break;
            case "Internacional":
                pedidoTipo = new PedidoInternacional();
                break;
            default:
                pedidoTipo = new PedidoEstandar();
        }

        MetodoDePago metodoPago = new PagoConTarjeta();

        Pedido pedido1 = new Pedido("P001", cliente1, productos, pedidoTipo, metodoPago);

        System.out.println("Estado del pedido antes del pago: " + pedido1.getEstado());
        pedido1.pagar();
        System.out.println("Estado del pedido después del pago: " + pedido1.getEstado());
    }
}
