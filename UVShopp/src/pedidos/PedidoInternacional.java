package pedidos;

import clientes.Cliente;

public class PedidoInternacional implements TipoPedido {
    @Override
    public double calcularCostoExtra(Cliente cliente) {
        return cliente.tieneEnvioGratis() ? 0 : 20000;
    }
    @Override
    public double calcularImpuestos(double subtotal) { return subtotal * 0.25; }
    @Override
    public String getDescripcion() { return "Pedido Internacional"; }
}

