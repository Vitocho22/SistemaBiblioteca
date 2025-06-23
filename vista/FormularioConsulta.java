package vista;

import dao.LibroDAO;
import dao.PrestamoDAO;
import dao.UsuarioDAO;
import modelo.Libro;
import modelo.Prestamo;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
// import java.awt.event.*;
import java.util.List;

public class FormularioConsulta extends JFrame {
    private JTextField txtTitulo, txtAutor, txtEditorial;
    private JButton btnBuscar, btnDisponibles, btnPrestados, btnActivos, btnFinalizados;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public FormularioConsulta() {
        setTitle("Consultas de Biblioteca");
        setLayout(null);

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(10, 10, 50, 25);
        add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(60, 10, 150, 25);
        add(txtTitulo);

        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setBounds(220, 10, 50, 25);
        add(lblAutor);

        txtAutor = new JTextField();
        txtAutor.setBounds(270, 10, 150, 25);
        add(txtAutor);

        JLabel lblEditorial = new JLabel("Editorial:");
        lblEditorial.setBounds(430, 10, 60, 25);
        add(lblEditorial);

        txtEditorial = new JTextField();
        txtEditorial.setBounds(490, 10, 150, 25);
        add(txtEditorial);

        btnBuscar = new JButton("Buscar Libros");
        btnBuscar.setBounds(10, 45, 150, 25);
        add(btnBuscar);

        btnDisponibles = new JButton("Libros Disponibles");
        btnDisponibles.setBounds(170, 45, 160, 25);
        add(btnDisponibles);

        btnPrestados = new JButton("Libros Prestados");
        btnPrestados.setBounds(340, 45, 150, 25);
        add(btnPrestados);

        btnActivos = new JButton("Préstamos Activos");
        btnActivos.setBounds(500, 45, 150, 25);
        add(btnActivos);

        btnFinalizados = new JButton("Préstamos Finalizados");
        btnFinalizados.setBounds(660, 45, 160, 25);
        add(btnFinalizados);

        modeloTabla = new DefaultTableModel();
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10, 80, 810, 300);
        add(scroll);

        btnBuscar.addActionListener(e -> buscarLibros());
        btnDisponibles.addActionListener(e -> listarLibros(true));
        btnPrestados.addActionListener(e -> listarLibros(false));
        btnActivos.addActionListener(e -> listarPrestamos(false));
        btnFinalizados.addActionListener(e -> listarPrestamos(true));

        setSize(850, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void buscarLibros() {
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnIdentifiers(new String[]{"ID", "Título", "Autor", "Editorial", "Año", "Disponible"});

        List<Libro> lista = new LibroDAO().listarLibros();
        for (Libro l : lista) {
            if (l.getTitulo().toLowerCase().contains(txtTitulo.getText().toLowerCase()) &&
                l.getAutor().toLowerCase().contains(txtAutor.getText().toLowerCase()) &&
                l.getEditorial().toLowerCase().contains(txtEditorial.getText().toLowerCase())) {

                modeloTabla.addRow(new Object[]{
                    l.getId(), l.getTitulo(), l.getAutor(), l.getEditorial(), l.getAnio(), l.isDisponible()
                });
            }
        }
    }

    private void listarLibros(boolean disponibles) {
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnIdentifiers(new String[]{"ID", "Título", "Autor", "Editorial", "Año", "Disponible"});

        List<Libro> lista = new LibroDAO().listarLibros();
        for (Libro l : lista) {
            if (l.isDisponible() == disponibles) {
                modeloTabla.addRow(new Object[]{
                    l.getId(), l.getTitulo(), l.getAutor(), l.getEditorial(), l.getAnio(), l.isDisponible()
                });
            }
        }
    }

    private void listarPrestamos(boolean devueltos) {
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnIdentifiers(new String[]{
            "ID", "Libro", "Usuario", "F. Préstamo", "F. Devolución", "Devuelto"
        });

        PrestamoDAO dao = new PrestamoDAO();
        List<Prestamo> lista = dao.listarPrestamos();
        for (Prestamo p : lista) {
            if (p.isDevuelto() == devueltos) {
                Libro l = obtenerLibro(p.getIdLibro());
                Usuario u = obtenerUsuario(p.getIdUsuario());
                modeloTabla.addRow(new Object[]{
                    p.getId(),
                    l != null ? l.getTitulo() : "Desconocido",
                    u != null ? u.getNombre() : "Desconocido",
                    p.getFechaPrestamo(),
                    p.getFechaDevolucion(),
                    p.isDevuelto()
                });
            }
        }
    }

    private Libro obtenerLibro(int id) {
        for (Libro l : new LibroDAO().listarLibros()) {
            if (l.getId() == id) return l;
        }
        return null;
    }

    private Usuario obtenerUsuario(int id) {
        for (Usuario u : new UsuarioDAO().listarUsuarios()) {
            if (u.getId() == id) return u;
        }
        return null;
    }
}
