package clientes;

public class Cliente {
    public enum TipoCliente { NUEVO, FRECUENTE, VIP }

    private String nombre;
    private String email;
    private String direccion;
    private TipoCliente tipo;

    public Cliente(String nombre, String email, String direccion, String tipoClienteStr) {
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        switch (tipoClienteStr.toUpperCase()) {
            case "NUEVO": this.tipo = TipoCliente.NUEVO; break;
            case "FRECUENTE": this.tipo = TipoCliente.FRECUENTE; break;
            case "VIP": this.tipo = TipoCliente.VIP; break;
            default: this.tipo = TipoCliente.NUEVO;
        }
    }

    public double getDescuento() {
        switch (tipo) {
            case NUEVO: return 0.05;
            case FRECUENTE: return 0.10;
            case VIP: return 0.15;
            default: return 0;
        }
    }

    public boolean tieneEnvioGratis() {
        return tipo == TipoCliente.VIP;
    }

    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getDireccion() { return direccion; }
    public TipoCliente getTipo() { return tipo; }
}
