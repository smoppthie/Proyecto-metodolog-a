package Interfaz;

import clientes.Cliente;
import productos.Producto;
import pedidos.Pedido;
import pagos.MetodoDePago;
import pagos.PagoConTarjeta;
import pagos.PagoConTransferencia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class MainUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField txtNombre, txtEmail, txtDireccion;
    private JComboBox<String> comboTipoCliente, comboProductos, comboTipoPedido, comboMetodoPago;
    private JButton btnCliente, btnAdministrador, btnCrearPedido;
    private JTextArea textArea;

    // Lista de productos disponibles
    private ArrayList<Producto> productosDisponibles;

    // Lista estática para almacenar los pedidos creados
    private static ArrayList<Pedido> pedidos = new ArrayList<>();

    // Lista dinámica para métodos de pago disponibles
    private static ArrayList<String> metodosPagoDisponibles = new ArrayList<>(Arrays.asList("Tarjeta", "Transferencia"));

    public MainUI() {
        // Inicializar productos disponibles
        productosDisponibles = new ArrayList<>();
        productosDisponibles.add(new Producto("Producto A", "A001", 100.0, 50));
        productosDisponibles.add(new Producto("Producto B", "B001", 200.0, 30));
        productosDisponibles.add(new Producto("Producto C", "C001", 150.0, 20));

        // Crear ventana inicial selección de rol
        frame = new JFrame("Seleccionar Rol");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        panel = new JPanel(new GridLayout(0, 1));

        btnCliente = new JButton("Soy Cliente");
        btnAdministrador = new JButton("Soy Administrador");

        panel.add(btnCliente);
        panel.add(btnAdministrador);

        frame.add(panel);
        frame.setVisible(true);

        btnCliente.addActionListener(e -> {
            frame.setVisible(false);
            abrirInterfazCliente();
        });

        btnAdministrador.addActionListener(e -> {
            frame.setVisible(false);
            abrirInterfazAdministrador();
        });
    }

    private void abrirInterfazCliente() {
        JFrame clienteFrame = new JFrame("Interfaz Cliente");
        clienteFrame.setSize(500, 550);
        clienteFrame.setLocationRelativeTo(null);
        JPanel panelCliente = new JPanel(new GridLayout(0, 2, 10, 10));

        panelCliente.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelCliente.add(txtNombre);

        panelCliente.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelCliente.add(txtEmail);

        panelCliente.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelCliente.add(txtDireccion);

        panelCliente.add(new JLabel("Tipo de Cliente (Nuevo, Frecuente, VIP):"));
        String[] tiposCliente = {"Nuevo", "Frecuente", "VIP"};
        comboTipoCliente = new JComboBox<>(tiposCliente);
        panelCliente.add(comboTipoCliente);

        panelCliente.add(new JLabel("Seleccionar Producto:"));
        comboProductos = new JComboBox<>();
        for (Producto producto : productosDisponibles) {
            comboProductos.addItem(producto.getNombre());
        }
        panelCliente.add(comboProductos);

        panelCliente.add(new JLabel("Tipo de Pedido:"));
        String[] tiposPedido = {"Estándar", "Exprés", "Programado", "Internacional"};
        comboTipoPedido = new JComboBox<>(tiposPedido);
        panelCliente.add(comboTipoPedido);

        panelCliente.add(new JLabel("Seleccionar Método de Pago:"));
        // Cargar desde la lista dinámica cada vez que se abre la ventana cliente
        comboMetodoPago = new JComboBox<>(metodosPagoDisponibles.toArray(new String[0]));
        panelCliente.add(comboMetodoPago);

        btnCrearPedido = new JButton("Crear Pedido");
        panelCliente.add(btnCrearPedido);

        JButton btnVolverCliente = new JButton("Volver");
        panelCliente.add(btnVolverCliente);

        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panelCliente.add(scrollPane);

        clienteFrame.add(panelCliente);
        clienteFrame.setVisible(true);

        btnCrearPedido.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String email = txtEmail.getText();
            String direccion = txtDireccion.getText();
            String tipoCliente = (String) comboTipoCliente.getSelectedItem();
            Cliente cliente = new Cliente(nombre, email, direccion, tipoCliente);

            Producto productoSeleccionado = productosDisponibles.get(comboProductos.getSelectedIndex());

            ArrayList<Producto> productosSeleccionados = new ArrayList<>();
            productosSeleccionados.add(productoSeleccionado);

            String tipoPedido = (String) comboTipoPedido.getSelectedItem();

            String metodoPago = (String) comboMetodoPago.getSelectedItem();
            MetodoDePago metodo;

            if (metodoPago.equals("Tarjeta")) {
                metodo = new PagoConTarjeta();
            } else if (metodoPago.equals("Transferencia")) {
                metodo = new PagoConTransferencia();
            } else {
                JOptionPane.showMessageDialog(clienteFrame, "Método de pago no implementado, se usará Tarjeta por defecto.");
                metodo = new PagoConTarjeta();
            }

            Pedido pedido = new Pedido("P001", cliente, productosSeleccionados, metodo, tipoPedido);

            productoSeleccionado.reducirStock(1);

            pedidos.add(pedido);

            textArea.setText("Pedido Creado:\n");
            textArea.append("Cliente: " + cliente.getNombre() + "\n");
            textArea.append("Producto: " + productoSeleccionado.getNombre() + "\n");
            textArea.append("Precio: " + productoSeleccionado.getPrecio() + "\n");
            textArea.append("Stock: " + productoSeleccionado.getCantidadStock() + "\n");
            textArea.append("Código: " + productoSeleccionado.getCodigo() + "\n");
            textArea.append("Tipo de Pedido: " + tipoPedido + "\n");
            textArea.append("Total: " + pedido.calcularTotal() + "\n");
            textArea.append("Método de Pago: " + metodoPago + "\n");
            textArea.append("Stock Restante: " + productoSeleccionado.getCantidadStock() + "\n");
        });

        btnVolverCliente.addActionListener(e -> {
            clienteFrame.dispose();
            frame.setVisible(true);
        });
    }

    private void abrirInterfazAdministrador() {
        JFrame adminFrame = new JFrame("Interfaz Administrador");
        adminFrame.setSize(500, 500);
        adminFrame.setLocationRelativeTo(null);
        JPanel panelAdmin = new JPanel(new GridLayout(0, 1, 10, 10));

        JButton btnVerPedidos = new JButton("Ver Pedidos");
        JButton btnAdministrarEnvios = new JButton("Administrar Envíos");

        JTextField txtNuevoMetodoPago = new JTextField();
        JButton btnAgregarNuevoMetodoPago = new JButton("Agregar Método de Pago");

        panelAdmin.add(btnVerPedidos);
        panelAdmin.add(new JLabel("Nuevo Método de Pago:"));
        panelAdmin.add(txtNuevoMetodoPago);
        panelAdmin.add(btnAgregarNuevoMetodoPago);
        panelAdmin.add(btnAdministrarEnvios);

        JButton btnVolverAdmin = new JButton("Volver");
        panelAdmin.add(btnVolverAdmin);

        JTextArea areaAdmin = new JTextArea(10, 30);
        areaAdmin.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaAdmin);
        panelAdmin.add(scrollPane);

        adminFrame.add(panelAdmin);
        adminFrame.setVisible(true);

        btnVerPedidos.addActionListener(e -> {
            areaAdmin.setText("Detalles de los Pedidos:\n");
            for (Pedido pedido : pedidos) {
                areaAdmin.append("ID: " + pedido.getId() + "\n");
                areaAdmin.append("Cliente: " + pedido.getCliente().getNombre() + "\n");
                areaAdmin.append("Tipo de Pedido: " + pedido.getTipoPedido() + "\n");
                areaAdmin.append("Estado: " + pedido.getEstado() + "\n");
                areaAdmin.append("Total: " + pedido.calcularTotal() + "\n\n");
            }
        });

        btnAgregarNuevoMetodoPago.addActionListener(e -> {
            String nuevoMetodo = txtNuevoMetodoPago.getText().trim();
            if (nuevoMetodo.isEmpty()) {
                JOptionPane.showMessageDialog(adminFrame, "Ingrese un nombre válido para el método de pago.");
                return;
            }
            if (metodosPagoDisponibles.contains(nuevoMetodo)) {
                JOptionPane.showMessageDialog(adminFrame, "Este método de pago ya existe.");
                return;
            }
            metodosPagoDisponibles.add(nuevoMetodo);
            JOptionPane.showMessageDialog(adminFrame, "Método de pago agregado correctamente: " + nuevoMetodo);
            txtNuevoMetodoPago.setText("");
        });

        btnAdministrarEnvios.addActionListener(e -> {
            JOptionPane.showMessageDialog(adminFrame, "Funcionalidad de Administrar Envíos no implementada aún.");
        });

        btnVolverAdmin.addActionListener(e -> {
            adminFrame.dispose();
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainUI::new);
    }
}
