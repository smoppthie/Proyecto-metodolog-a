package interfaz;

import clientes.Cliente;
import productos.Producto;
import pedidos.*;
import pagos.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class MainUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField txtNombre, txtEmail, txtDireccion;
    private JComboBox<String> comboTipoCliente, comboProductos, comboTipoPedido, comboMetodoPago;
    private JButton btnCliente, btnAdministrador, btnCrearPedido;
    private JTextArea textArea;

    private ArrayList<Producto> productosDisponibles;

    private static ArrayList<Pedido> pedidos = new ArrayList<>();
    private static ArrayList<String> metodosPagoDisponibles = new ArrayList<>(
            Arrays.asList("Tarjeta", "Transferencia", "Criptomoneda", "Contra Entrega"));

    public MainUI() {
        productosDisponibles = new ArrayList<>();
        productosDisponibles.add(new Producto("Producto A", "A001", 100.0, 50));
        productosDisponibles.add(new Producto("Producto B", "B001", 200.0, 30));
        productosDisponibles.add(new Producto("Producto C", "C001", 150.0, 20));

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

    private TipoPedido obtenerTipoPedidoDesdeString(String tipo) {
        switch (tipo) {
            case "Estándar": return new PedidoEstandar();
            case "Exprés": return new PedidoExpres();
            case "Programado": return new PedidoProgramado(LocalDate.now().plusDays(3));
            case "Internacional": return new PedidoInternacional();
            default: return new PedidoEstandar();
        }
    }

    private MetodoDePago obtenerMetodoPagoDesdeString(String metodo) {
        switch (metodo) {
            case "Tarjeta": return new PagoConTarjeta();
            case "Transferencia": return new PagoConTransferencia();
            case "Criptomoneda": return new PagoConCriptomoneda();
            case "Contra Entrega": return new PagoContraEntrega();
            default: return new PagoConTarjeta();
        }
    }

    private void abrirInterfazCliente() {
        JFrame clienteFrame = new JFrame("Interfaz Cliente");
        clienteFrame.setSize(600, 650);
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
        comboMetodoPago = new JComboBox<>(metodosPagoDisponibles.toArray(new String[0]));
        panelCliente.add(comboMetodoPago);

        btnCrearPedido = new JButton("Crear Pedido");
        panelCliente.add(btnCrearPedido);

        JButton btnVolverCliente = new JButton("Volver");
        panelCliente.add(btnVolverCliente);

        // Campo para ingresar ID de pedido para pagar
        JLabel lblIdPagar = new JLabel("ID del Pedido para pagar:");
        JTextField txtIdPagar = new JTextField();
        panelCliente.add(lblIdPagar);
        panelCliente.add(txtIdPagar);

        JButton btnPagarPedido = new JButton("Pagar Pedido");
        panelCliente.add(btnPagarPedido);

        // Espacio vacío para mantener cuadrícula
        panelCliente.add(new JLabel(""));

        textArea = new JTextArea(10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panelCliente.add(scrollPane);

        clienteFrame.add(panelCliente);
        clienteFrame.setVisible(true);

        btnCrearPedido.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();
                String email = txtEmail.getText();
                String direccion = txtDireccion.getText();
                String tipoClienteStr = (String) comboTipoCliente.getSelectedItem();
                Cliente cliente = new Cliente(nombre, email, direccion, tipoClienteStr);

                Producto productoSeleccionado = productosDisponibles.get(comboProductos.getSelectedIndex());
                ArrayList<Producto> productosSeleccionados = new ArrayList<>();
                productosSeleccionados.add(productoSeleccionado);

                String tipoPedidoStr = (String) comboTipoPedido.getSelectedItem();
                TipoPedido tipoPedido = obtenerTipoPedidoDesdeString(tipoPedidoStr);

                String metodoPagoStr = (String) comboMetodoPago.getSelectedItem();
                MetodoDePago metodoPago = obtenerMetodoPagoDesdeString(metodoPagoStr);

                Pedido pedido = new Pedido("P" + (pedidos.size() + 1), cliente, productosSeleccionados, tipoPedido, metodoPago);

                productoSeleccionado.reducirStock(1);

                pedidos.add(pedido);

                textArea.setText("Pedido Creado:\n");
                textArea.append("ID: " + pedido.getId() + "\n");
                textArea.append("Cliente: " + cliente.getNombre() + "\n");
                textArea.append("Producto: " + productoSeleccionado.getNombre() + "\n");
                textArea.append("Precio: " + productoSeleccionado.getPrecio() + "\n");
                textArea.append("Stock: " + productoSeleccionado.getCantidadStock() + "\n");
                textArea.append("Código: " + productoSeleccionado.getCodigo() + "\n");
                textArea.append("Tipo de Pedido: " + tipoPedido.getDescripcion() + "\n");
                textArea.append("Total: " + pedido.calcularTotal() + "\n");
                textArea.append("Método de Pago: " + metodoPagoStr + "\n");
                textArea.append("Stock Restante: " + productoSeleccionado.getCantidadStock() + "\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(clienteFrame, "Error: " + ex.getMessage());
            }
        });

        btnPagarPedido.addActionListener(e -> {
            String idPagar = txtIdPagar.getText().trim();
            if (idPagar.isEmpty()) {
                JOptionPane.showMessageDialog(clienteFrame, "Ingrese un ID válido.");
                return;
            }
            Pedido pedido = buscarPedidoPorId(idPagar);
            if (pedido == null) {
                JOptionPane.showMessageDialog(clienteFrame, "Pedido no encontrado.");
                return;
            }
            try {
                pedido.pagar();
                JOptionPane.showMessageDialog(clienteFrame, "Pedido " + idPagar + " pagado exitosamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(clienteFrame, "Error al pagar: " + ex.getMessage());
            }
        });

        btnVolverCliente.addActionListener(e -> {
            clienteFrame.dispose();
            frame.setVisible(true);
        });
    }

    private void abrirInterfazAdministrador() {
        JFrame adminFrame = new JFrame("Interfaz Administrador");
        adminFrame.setSize(600, 600);
        adminFrame.setLocationRelativeTo(null);
        JPanel panelAdmin = new JPanel(new GridLayout(0, 1, 10, 10));

        JButton btnVerPedidos = new JButton("Ver Pedidos");
        JButton btnPrepararEnvio = new JButton("Preparar Envío");
        JButton btnEnviarPedido = new JButton("Enviar Pedido");
        JButton btnCancelarPedido = new JButton("Cancelar Pedido");
        JButton btnAgregarMetodoPago = new JButton("Agregar Método de Pago");
        JTextField txtNuevoMetodoPago = new JTextField();

        JButton btnVolverAdmin = new JButton("Volver");

        JTextArea areaAdmin = new JTextArea(15, 40);
        areaAdmin.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaAdmin);

        panelAdmin.add(btnVerPedidos);
        panelAdmin.add(btnPrepararEnvio);
        panelAdmin.add(btnEnviarPedido);
        panelAdmin.add(btnCancelarPedido);
        panelAdmin.add(new JLabel("Nuevo Método de Pago:"));
        panelAdmin.add(txtNuevoMetodoPago);
        panelAdmin.add(btnAgregarMetodoPago);
        panelAdmin.add(btnVolverAdmin);
        panelAdmin.add(scrollPane);

        adminFrame.add(panelAdmin);
        adminFrame.setVisible(true);

        btnVerPedidos.addActionListener(e -> {
            areaAdmin.setText("Lista de Pedidos:\n");
            for (Pedido pedido : pedidos) {
                areaAdmin.append("ID: " + pedido.getId() + " | Cliente: " + pedido.getCliente().getNombre() +
                        " | Tipo: " + pedido.getTipoPedido().getDescripcion() + " | Estado: " + pedido.getEstado() +
                        " | Total: $" + pedido.calcularTotal() + "\n");
            }
        });

        btnPrepararEnvio.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(adminFrame, "Ingrese ID del pedido para preparar envío:");
            Pedido pedido = buscarPedidoPorId(id);
            if (pedido != null) {
                try {
                    pedido.prepararEnvio();
                    areaAdmin.append("Pedido " + id + " cambiado a estado EN PREPARACIÓN.\n");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(adminFrame, "Error: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(adminFrame, "Pedido no encontrado.");
            }
        });

        btnEnviarPedido.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(adminFrame, "Ingrese ID del pedido para enviar:");
            Pedido pedido = buscarPedidoPorId(id);
            if (pedido != null) {
                try {
                    pedido.enviar();
                    areaAdmin.append("Pedido " + id + " cambiado a estado ENVIADO.\n");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(adminFrame, "Error: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(adminFrame, "Pedido no encontrado.");
            }
        });

        btnCancelarPedido.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(adminFrame, "Ingrese ID del pedido para cancelar:");
            Pedido pedido = buscarPedidoPorId(id);
            if (pedido != null) {
                try {
                    pedido.cancelar();
                    areaAdmin.append("Pedido " + id + " ha sido CANCELADO.\n");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(adminFrame, "Error: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(adminFrame, "Pedido no encontrado.");
            }
        });

        btnAgregarMetodoPago.addActionListener(e -> {
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

        btnVolverAdmin.addActionListener(e -> {
            adminFrame.dispose();
            frame.setVisible(true);
        });
    }

    private Pedido buscarPedidoPorId(String id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId().equalsIgnoreCase(id)) {
                return pedido;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainUI::new);
    }
}

