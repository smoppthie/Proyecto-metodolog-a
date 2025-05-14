import productos.Producto;
import clientes.Cliente;
import pagos.PagoConTarjeta;
import pagos.MetodoDePago;
import pedidos.Pedido;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crear productos
        Producto producto1 = new Producto("Producto A", "A001", 100.0, 50);
        Producto producto2 = new Producto("Producto B", "B001", 200.0, 30);

        // Crear cliente
        Cliente cliente1 = new Cliente("Juan Pérez", "juan@correo.com", "Calle 123", "Frecuente");

        // Crear lista de productos
        List<Producto> productos = new ArrayList<>();
        productos.add(producto1);
        productos.add(producto2);

        // Crear el tipo de pedido
        String tipoPedido = "Estándar"; // Tipo de pedido que puedes cambiar según lo necesites

        // Crear el pedido con método de pago con tarjeta
        Pedido pedido1 = new Pedido("P001", cliente1, productos, new PagoConTarjeta(), tipoPedido);

        // Realizar pago
        System.out.println("Estado del pedido antes del pago: " + pedido1.getEstado());
        pedido1.pagar();
        System.out.println("Estado del pedido después del pago: " + pedido1.getEstado());
    }
}
