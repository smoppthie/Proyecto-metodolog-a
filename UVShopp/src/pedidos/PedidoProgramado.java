package pedidos;

import clientes.Cliente;

import java.time.LocalDate;

public class PedidoProgramado implements TipoPedido {
    private LocalDate fechaEntrega;

    public PedidoProgramado(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    @Override
    public double calcularCostoExtra(Cliente cliente) { return 1000; }
    @Override
    public double calcularImpuestos(double subtotal) { return subtotal * 0.19; }
    @Override
    public String getDescripcion() { return "Pedido Programado para " + fechaEntrega; }

    public LocalDate getFechaEntrega() { return fechaEntrega; }
}
