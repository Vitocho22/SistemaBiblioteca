package vista;

import modelo.Usuario;
import dao.UsuarioDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class FormularioUsuario extends JFrame {
    private JTextField txtNombre, txtCorreo;
    private JComboBox<String> cmbTipo;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private Usuario usuarioSeleccionado = null;

    public FormularioUsuario() {
        setTitle("Gestión de Usuarios");
        setLayout(null);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(10, 10, 80, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 10, 200, 25);
        add(txtNombre);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(10, 40, 80, 25);
        add(lblTipo);

        cmbTipo = new JComboBox<>(new String[]{"estudiante", "docente"});
        cmbTipo.setBounds(100, 40, 200, 25);
        add(cmbTipo);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(10, 70, 80, 25);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(100, 70, 200, 25);
        add(txtCorreo);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(10, 110, 90, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(110, 110, 100, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(220, 110, 90, 30);
        add(btnEliminar);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Tipo", "Correo"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10, 160, 460, 200);
        add(scroll);

        cargarUsuarios();

        btnGuardar.addActionListener(e -> {
            Usuario usuario = new Usuario(
                txtNombre.getText(),
                cmbTipo.getSelectedItem().toString(),
                txtCorreo.getText()
            );
            new UsuarioDAO().registrarUsuario(usuario);
            limpiarCampos();
            cargarUsuarios();
        });

        btnActualizar.addActionListener(e -> {
            if (usuarioSeleccionado != null) {
                usuarioSeleccionado.setNombre(txtNombre.getText());
                usuarioSeleccionado.setTipo(cmbTipo.getSelectedItem().toString());
                usuarioSeleccionado.setCorreo(txtCorreo.getText());

                new UsuarioDAO().actualizarUsuario(usuarioSeleccionado);
                limpiarCampos();
                cargarUsuarios();
                usuarioSeleccionado = null;
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario para actualizar.");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (usuarioSeleccionado != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new UsuarioDAO().eliminarUsuario(usuarioSeleccionado.getId());
                    limpiarCampos();
                    cargarUsuarios();
                    usuarioSeleccionado = null;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario para eliminar.");
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                String nombre = modeloTabla.getValueAt(fila, 1).toString();
                String tipo = modeloTabla.getValueAt(fila, 2).toString();
                String correo = modeloTabla.getValueAt(fila, 3).toString();

                usuarioSeleccionado = new Usuario(id, nombre, tipo, correo);
                txtNombre.setText(nombre);
                cmbTipo.setSelectedItem(tipo);
                txtCorreo.setText(correo);
            }
        });

        setSize(500, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void cargarUsuarios() {
        modeloTabla.setRowCount(0);
        List<Usuario> lista = new UsuarioDAO().listarUsuarios();
        for (Usuario u : lista) {
            modeloTabla.addRow(new Object[]{
                u.getId(), u.getNombre(), u.getTipo(), u.getCorreo()
            });
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtCorreo.setText("");
        cmbTipo.setSelectedIndex(0);
        tabla.clearSelection();
    }
}
