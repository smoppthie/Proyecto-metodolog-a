package utils;

import clientes.Cliente;

public class DescuentoUtils {
    public static double calcularDescuento(Cliente cliente, double total) {
        return total - (total * cliente.getDescuento());
    }
}
