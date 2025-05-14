package pagos;

public class PagoContraEntrega implements MetodoDePago {
    @Override
    public void procesarPago(double monto) {
        System.out.println("Pago contra entrega por $" + monto);
    }
}
