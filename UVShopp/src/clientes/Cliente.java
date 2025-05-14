package clientes;

public class Cliente {
    private String nombre;
    private String email;
    private String direccion;
    private String tipoCliente; // Nuevo, Frecuente, VIP

    // Constructor
    public Cliente(String nombre, String email, String direccion, String tipoCliente) {
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.tipoCliente = tipoCliente;
    }

    // Método para obtener el descuento como un número decimal
    public double obtenerDescuento() {
        switch(tipoCliente) {
            case "Nuevo":
                return 0.05;  // 5% descuento
            case "Frecuente":
                return 0.10;  // 10% descuento
            case "VIP":
                return 0.15;  // 15% descuento + envío gratis
            default:
                return 0;  // Sin descuento
        }
    }

    // Otros métodos
    public String getNombre() {
        return nombre;
    }
}
