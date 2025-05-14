package pagos;

public class PagoConTransferencia implements MetodoDePago {
    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando pago por transferencia por un monto de: " + monto);
    }
}
