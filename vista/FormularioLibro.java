package vista;

import modelo.Libro;
import dao.LibroDAO;

import javax.swing.*;
import java.awt.event.*;

public class FormularioLibro extends JFrame {
    private JTextField txtTitulo, txtAutor, txtEditorial, txtAnio;
    private JCheckBox chkDisponible;
    private JButton btnGuardar;

    public FormularioLibro() {
        setTitle("Registrar Libro");
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
        chkDisponible.setBounds(100, 130, 100, 25);
        add(chkDisponible);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(100, 170, 100, 30);
        add(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Libro libro = new Libro(
                    txtTitulo.getText(),
                    txtAutor.getText(),
                    txtEditorial.getText(),
                    Integer.parseInt(txtAnio.getText()),
                    chkDisponible.isSelected()
                );
                LibroDAO dao = new LibroDAO();
                dao.registrarLibro(libro);
            }
        });

        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
