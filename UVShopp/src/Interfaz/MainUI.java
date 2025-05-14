package Interfaz;

import clientes.Cliente;
import productos.Producto;
import pedidos.Pedido;
import pagos.MetodoDePago;
import pagos.PagoConTarjeta;
import pagos.PagoConTransferencia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField txtNombre, txtEmail, txtDireccion;
    private JComboBox<String> comboTipoCliente, comboProductos, comboTipoPedido, comboMetodoPago;
    private JButton btnCliente, btnAdministrador, btnCrearPedido;
    private JTextArea textArea;

    // Lista de productos disponibles
    private ArrayList<Producto> productosDisponibles;

    public MainUI() {
        // Inicialización de productos disponibles
        productosDisponibles = new ArrayList<>();
        productosDisponibles.add(new Producto("Producto A", "A001", 100.0, 50));
        productosDisponibles.add(new Producto("Producto B", "B001", 200.0, 30));
        productosDisponibles.add(new Producto("Producto C", "C001", 150.0, 20));

        // Crear la ventana de selección de rol
        frame = new JFrame("Seleccionar Rol");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        // Crear botones para elegir rol
        btnCliente = new JButton("Soy Cliente");
        btnAdministrador = new JButton("Soy Administrador");

        // Agregar botones al panel
        panel.add(btnCliente);
        panel.add(btnAdministrador);

        // Agregar panel al marco
        frame.add(panel);
        frame.setVisible(true);

        // Acción para seleccionar Cliente
        btnCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);  // Ocultar la ventana de selección de rol
                abrirInterfazCliente();   // Abrir la interfaz de Cliente
            }
        });

        // Acción para seleccionar Administrador
        btnAdministrador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);  // Ocultar la ventana de selección de rol
                abrirInterfazAdministrador();   // Abrir la interfaz de Administrador
            }
        });
    }

    // Interfaz de Cliente
    private void abrirInterfazCliente() {
        JFrame clienteFrame = new JFrame("Interfaz Cliente");
        clienteFrame.setSize(500, 500);
        clienteFrame.setLocationRelativeTo(null);
        JPanel panelCliente = new JPanel();
        panelCliente.setLayout(new GridLayout(0, 2));

        // Crear campos de texto para la información del cliente
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
        comboTipoCliente = new JComboBox<>(tiposCliente);  // Usamos JComboBox en vez de JTextField
        panelCliente.add(comboTipoCliente);

        // Crear combo box para seleccionar productos
        panelCliente.add(new JLabel("Seleccionar Producto:"));
        comboProductos = new JComboBox<>();
        for (Producto producto : productosDisponibles) {
            comboProductos.addItem(producto.getNombre());
        }
        panelCliente.add(comboProductos);

        // Crear combo box para seleccionar tipo de pedido
        panelCliente.add(new JLabel("Tipo de Pedido:"));
        String[] tiposPedido = {"Estándar", "Exprés", "Programado", "Internacional"};
        comboTipoPedido = new JComboBox<>(tiposPedido);
        panelCliente.add(comboTipoPedido);

        // Crear combo box para seleccionar el método de pago
        panelCliente.add(new JLabel("Seleccionar Método de Pago:"));
        String[] metodosPago = {"Tarjeta", "Transferencia"};
        comboMetodoPago = new JComboBox<>(metodosPago);
        panelCliente.add(comboMetodoPago);

        // Botón para crear el pedido
        btnCrearPedido = new JButton("Crear Pedido");
        panelCliente.add(btnCrearPedido);

        // Área de texto para mostrar el resumen
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panelCliente.add(scrollPane);

        clienteFrame.add(panelCliente);
        clienteFrame.setVisible(true);

        // Acción para crear el pedido
        btnCrearPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear el cliente con los datos ingresados
                String nombre = txtNombre.getText();
                String email = txtEmail.getText();
                String direccion = txtDireccion.getText();
                String tipoCliente = (String) comboTipoCliente.getSelectedItem();  // Obtener tipo de cliente
                Cliente cliente = new Cliente(nombre, email, direccion, tipoCliente);

                // Seleccionar el producto elegido
                Producto productoSeleccionado = productosDisponibles.get(comboProductos.getSelectedIndex());

                // Crear una lista con el producto seleccionado
                ArrayList<Producto> productosSeleccionados = new ArrayList<>();
                productosSeleccionados.add(productoSeleccionado);

                // Seleccionar el tipo de pedido
                String tipoPedido = (String) comboTipoPedido.getSelectedItem();

                // Seleccionar el método de pago
                String metodoPago = (String) comboMetodoPago.getSelectedItem();
                MetodoDePago metodo = null;

                if (metodoPago.equals("Tarjeta")) {
                    metodo = new PagoConTarjeta();
                } else if (metodoPago.equals("Transferencia")) {
                    metodo = new PagoConTransferencia();
                }

                // Crear el pedido
                Pedido pedido = new Pedido("P001", cliente, productosSeleccionados, metodo, tipoPedido);

                // Descontar el stock del producto
                productoSeleccionado.reducirStock(1);  // Se asume que el cliente pide 1 unidad

                // Mostrar los detalles del pedido
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
            }
        });
    }

    // Interfaz de Administrador
    private void abrirInterfazAdministrador() {
        JFrame adminFrame = new JFrame("Interfaz Administrador");
        adminFrame.setSize(500, 400);
        adminFrame.setLocationRelativeTo(null);
        JPanel panelAdmin = new JPanel();
        panelAdmin.setLayout(new GridLayout(0, 1));

        // Agregar botones para administrar pedidos
        JButton btnVerPedidos = new JButton("Ver Pedidos");
        JButton btnAgregarMetodoPago = new JButton("Agregar Método de Pago");

        panelAdmin.add(btnVerPedidos);
        panelAdmin.add(btnAgregarMetodoPago);

        adminFrame.add(panelAdmin);
        adminFrame.setVisible(true);

        // Acciones de administrador
        btnVerPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(adminFrame, "Funcionalidad de Ver Pedidos no implementada aún.");
            }
        });

        btnAgregarMetodoPago.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(adminFrame, "Funcionalidad de Agregar Método de Pago no implementada aún.");
            }
        });
    }

    public static void main(String[] args) {
        // Iniciar la interfaz
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainUI();
            }
        });
    }
}
