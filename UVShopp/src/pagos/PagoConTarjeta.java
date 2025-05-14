package pagos;

public class PagoConTarjeta implements MetodoDePago {
    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando pago con tarjeta por un monto de: " + monto);
    }
}
