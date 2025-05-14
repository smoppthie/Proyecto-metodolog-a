package productos;

public class Producto {
    private String nombre;
    private String codigo;
    private double precio;
    private int cantidadStock;

    public Producto(String nombre, String codigo, double precio, int cantidadStock) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
    }

    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public double getPrecio() { return precio; }
    public int getCantidadStock() { return cantidadStock; }

    public void reducirStock(int cantidad) {
        if (cantidad <= cantidadStock) {
            this.cantidadStock -= cantidad;
        } else {
            throw new IllegalArgumentException("Stock insuficiente para " + nombre);
        }
    }
}

