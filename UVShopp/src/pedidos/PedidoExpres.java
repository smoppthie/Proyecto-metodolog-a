package pedidos;

import clientes.Cliente;

public class PedidoExpres implements TipoPedido {
    @Override
    public double calcularCostoExtra(Cliente cliente) { return 5000; }
    @Override
    public double calcularImpuestos(double subtotal) { return subtotal * 0.19; }
    @Override
    public String getDescripcion() { return "Pedido Exprés"; }
}
