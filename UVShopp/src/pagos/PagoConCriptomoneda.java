package pagos;

public class PagoConCriptomoneda implements MetodoDePago {
    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando pago con criptomoneda por $" + monto);
    }
}


