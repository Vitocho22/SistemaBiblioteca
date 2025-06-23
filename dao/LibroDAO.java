package dao;

import conexion.ConexionBD;
import modelo.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LibroDAO {
    public void registrarLibro(Libro libro) {
        String sql = "INSERT INTO Libro (titulo, autor, editorial, anio, disponible) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getEditorial());
            ps.setInt(4, libro.getAnio());
            ps.setBoolean(5, libro.isDisponible());
            ps.executeUpdate();
            System.out.println("Libro registrado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
