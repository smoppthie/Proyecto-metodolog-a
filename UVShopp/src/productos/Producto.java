package productos;

public class Producto {
    private String nombre;
    private String codigo;  // Código del producto
    private double precio;
    private int cantidadStock;

    // Constructor
    public Producto(String nombre, String codigo, double precio, int cantidadStock) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
    }

    // Métodos para obtener datos del producto
    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;  // Este es el método que falta
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void reducirStock(int cantidad) {
        this.cantidadStock -= cantidad;
    }
}

