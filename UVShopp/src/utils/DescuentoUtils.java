package utils;

import clientes.Cliente;

public class DescuentoUtils {
    public static double calcularDescuento(Cliente cliente, double total) {
        // Aplicar descuento
        return total - (total * cliente.obtenerDescuento());
    }
}
