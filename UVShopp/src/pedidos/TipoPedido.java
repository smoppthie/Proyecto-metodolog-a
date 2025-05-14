package pedidos;

import clientes.Cliente;

public interface TipoPedido {
    double calcularCostoExtra(Cliente cliente);
    double calcularImpuestos(double subtotal);
    String getDescripcion();
}
