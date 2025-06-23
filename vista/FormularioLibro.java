package vista;

import modelo.Libro;
import dao.LibroDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class FormularioLibro extends JFrame {
    private JTextField txtTitulo, txtAutor, txtEditorial, txtAnio;
    private JCheckBox chkDisponible;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private Libro libroSeleccionado = null;

    public FormularioLibro() {
        setTitle("Gestión de Libros");
        setLayout(null);

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(10, 10, 80, 25);
        add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(100, 10, 200, 25);
        add(txtTitulo);

        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setBounds(10, 40, 80, 25);
        add(lblAutor);

        txtAutor = new JTextField();
        txtAutor.setBounds(100, 40, 200, 25);
        add(txtAutor);

        JLabel lblEditorial = new JLabel("Editorial:");
        lblEditorial.setBounds(10, 70, 80, 25);
        add(lblEditorial);

        txtEditorial = new JTextField();
        txtEditorial.setBounds(100, 70, 200, 25);
        add(txtEditorial);

        JLabel lblAnio = new JLabel("Año:");
        lblAnio.setBounds(10, 100, 80, 25);
        add(lblAnio);

        txtAnio = new JTextField();
        txtAnio.setBounds(100, 100, 200, 25);
        add(txtAnio);

        chkDisponible = new JCheckBox("Disponible");
        chkDisponible.setBounds(100, 130, 120, 25);
        add(chkDisponible);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(10, 170, 90, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(110, 170, 100, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(220, 170, 90, 30);
        add(btnEliminar);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Título", "Autor", "Editorial", "Año", "Disponible"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10, 220, 560, 200);
        add(scroll);

        cargarLibros();

        btnGuardar.addActionListener(e -> {
            Libro libro = new Libro(
                txtTitulo.getText(),
                txtAutor.getText(),
                txtEditorial.getText(),
                Integer.parseInt(txtAnio.getText()),
                chkDisponible.isSelected()
            );
            new LibroDAO().registrarLibro(libro);
            limpiarCampos();
            cargarLibros();
        });

        btnActualizar.addActionListener(e -> {
            if (libroSeleccionado != null) {
                libroSeleccionado.setTitulo(txtTitulo.getText());
                libroSeleccionado.setAutor(txtAutor.getText());
                libroSeleccionado.setEditorial(txtEditorial.getText());
                libroSeleccionado.setAnio(Integer.parseInt(txtAnio.getText()));
                libroSeleccionado.setDisponible(chkDisponible.isSelected());

                new LibroDAO().actualizarLibro(libroSeleccionado);
                limpiarCampos();
                cargarLibros();
                libroSeleccionado = null;
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un libro para actualizar.");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (libroSeleccionado != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este libro?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new LibroDAO().eliminarLibro(libroSeleccionado.getId());
                    limpiarCampos();
                    cargarLibros();
                    libroSeleccionado = null;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un libro para eliminar.");
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                String titulo = modeloTabla.getValueAt(fila, 1).toString();
                String autor = modeloTabla.getValueAt(fila, 2).toString();
                String editorial = modeloTabla.getValueAt(fila, 3).toString();
                int anio = Integer.parseInt(modeloTabla.getValueAt(fila, 4).toString());
                boolean disponible = Boolean.parseBoolean(modeloTabla.getValueAt(fila, 5).toString());

                libroSeleccionado = new Libro(id, titulo, autor, editorial, anio, disponible);

                txtTitulo.setText(titulo);
                txtAutor.setText(autor);
                txtEditorial.setText(editorial);
                txtAnio.setText(String.valueOf(anio));
                chkDisponible.setSelected(disponible);
            }
        });

        setSize(600, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void cargarLibros() {
        modeloTabla.setRowCount(0);
        List<Libro> lista = new LibroDAO().listarLibros();
        for (Libro l : lista) {
            modeloTabla.addRow(new Object[]{
                l.getId(), l.getTitulo(), l.getAutor(), l.getEditorial(), l.getAnio(), l.isDisponible()
            });
        }
    }

    private void limpiarCampos() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtEditorial.setText("");
        txtAnio.setText("");
        chkDisponible.setSelected(false);
        tabla.clearSelection();
    }
}
