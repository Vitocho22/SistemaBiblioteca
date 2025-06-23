package vista;

import modelo.Prestamo;
import modelo.Libro;
import modelo.Usuario;
import dao.PrestamoDAO;
import dao.LibroDAO;
import dao.UsuarioDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import java.awt.event.*;
import java.util.*;
import java.util.List;

public class FormularioPrestamo extends JFrame {
    private JComboBox<Libro> cmbLibros;
    private JComboBox<Usuario> cmbUsuarios;
    private JDateChooser fechaPrestamo, fechaDevolucion;
    private JButton btnRegistrar, btnDevolver;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private Prestamo prestamoSeleccionado = null;

    public FormularioPrestamo() {
        setTitle("Gestión de Préstamos");
        setLayout(null);

        JLabel lblLibro = new JLabel("Libro:");
        lblLibro.setBounds(10, 10, 80, 25);
        add(lblLibro);

        cmbLibros = new JComboBox<>();
        cmbLibros.setBounds(100, 10, 300, 25);
        add(cmbLibros);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(10, 40, 80, 25);
        add(lblUsuario);

        cmbUsuarios = new JComboBox<>();
        cmbUsuarios.setBounds(100, 40, 300, 25);
        add(cmbUsuarios);

        JLabel lblFPrestamo = new JLabel("Fecha Préstamo:");
        lblFPrestamo.setBounds(10, 70, 120, 25);
        add(lblFPrestamo);

        fechaPrestamo = new JDateChooser();
        fechaPrestamo.setBounds(130, 70, 150, 25);
        add(fechaPrestamo);

        JLabel lblFDevolucion = new JLabel("Fecha Devolución:");
        lblFDevolucion.setBounds(10, 100, 120, 25);
        add(lblFDevolucion);

        fechaDevolucion = new JDateChooser();
        fechaDevolucion.setBounds(130, 100, 150, 25);
        add(fechaDevolucion);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(300, 70, 100, 25);
        add(btnRegistrar);

        btnDevolver = new JButton("Marcar Devolución");
        btnDevolver.setBounds(300, 100, 160, 25);
        add(btnDevolver);

        modeloTabla = new DefaultTableModel(new String[]{
            "ID", "Libro", "Usuario", "F. Préstamo", "F. Devolución", "Devuelto"
        }, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10, 150, 600, 250);
        add(scroll);

        cargarCombos();
        cargarTabla();

        btnRegistrar.addActionListener(e -> {
            Libro libro = (Libro) cmbLibros.getSelectedItem();
            Usuario usuario = (Usuario) cmbUsuarios.getSelectedItem();
            Date fPrestamo = fechaPrestamo.getDate();
            Date fDevolucion = fechaDevolucion.getDate();

            if (libro != null && usuario != null && fPrestamo != null && fDevolucion != null) {
                Prestamo p = new Prestamo(libro.getId(), usuario.getId(), fPrestamo, fDevolucion, false);
                new PrestamoDAO().registrarPrestamo(p);
                cargarCombos();
                cargarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                int idPrestamo = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
                boolean devuelto = Boolean.parseBoolean(tabla.getValueAt(fila, 5).toString());
                if (!devuelto) {
                    prestamoSeleccionado = new Prestamo(
                        idPrestamo,
                        Integer.parseInt(tabla.getValueAt(fila, 1).toString().split("-")[0]),
                        Integer.parseInt(tabla.getValueAt(fila, 2).toString().split("-")[0]),
                        null, null, false
                    );
                } else {
                    prestamoSeleccionado = null;
                }
            }
        });

        btnDevolver.addActionListener(e -> {
            if (prestamoSeleccionado != null) {
                new PrestamoDAO().marcarDevuelto(prestamoSeleccionado.getId(), prestamoSeleccionado.getIdLibro());
                cargarTabla();
                cargarCombos();
                prestamoSeleccionado = null;
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un préstamo activo.");
            }
        });

        setSize(640, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void cargarCombos() {
        cmbLibros.removeAllItems();
        for (Libro l : new LibroDAO().listarLibros()) {
            if (l.isDisponible()) {
                cmbLibros.addItem(l);
            }
        }

        cmbUsuarios.removeAllItems();
        for (Usuario u : new UsuarioDAO().listarUsuarios()) {
            cmbUsuarios.addItem(u);
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Prestamo> lista = new PrestamoDAO().listarPrestamos();
        for (Prestamo p : lista) {
            Libro l = obtenerLibroPorId(p.getIdLibro());
            Usuario u = obtenerUsuarioPorId(p.getIdUsuario());
            modeloTabla.addRow(new Object[]{
                p.getId(),
                l.getId() + "-" + l.getTitulo(),
                u.getId() + "-" + u.getNombre(),
                p.getFechaPrestamo(),
                p.getFechaDevolucion(),
                p.isDevuelto()
            });
        }
    }

    private Libro obtenerLibroPorId(int id) {
        for (Libro l : new LibroDAO().listarLibros()) {
            if (l.getId() == id) return l;
        }
        return new Libro(id, "Desconocido", "", "", 0, false);
    }

    private Usuario obtenerUsuarioPorId(int id) {
        for (Usuario u : new UsuarioDAO().listarUsuarios()) {
            if (u.getId() == id) return u;
        }
        return new Usuario(id, "Desconocido", "", "");
    }

    private void limpiarCampos() {
        fechaPrestamo.setDate(null);
        fechaDevolucion.setDate(null);
        tabla.clearSelection();
    }
}
