package dao;

import conexion.ConexionBD;
import modelo.Prestamo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    public void registrarPrestamo(Prestamo p) {
        String sql = "INSERT INTO Prestamo (id_libro, id_usuario, fecha_prestamo, fecha_devolucion, devuelto) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getIdLibro());
            ps.setInt(2, p.getIdUsuario());
            ps.setDate(3, new java.sql.Date(p.getFechaPrestamo().getTime()));
            ps.setDate(4, new java.sql.Date(p.getFechaDevolucion().getTime()));
            ps.setBoolean(5, false);
            ps.executeUpdate();

            // Marcar libro como no disponible
            PreparedStatement psLibro = con.prepareStatement("UPDATE Libro SET disponible = false WHERE id_libro = ?");
            psLibro.setInt(1, p.getIdLibro());
            psLibro.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Prestamo> listarPrestamos() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Prestamo";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Prestamo p = new Prestamo(
                    rs.getInt("id_prestamo"),
                    rs.getInt("id_libro"),
                    rs.getInt("id_usuario"),
                    rs.getDate("fecha_prestamo"),
                    rs.getDate("fecha_devolucion"),
                    rs.getBoolean("devuelto")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void marcarDevuelto(int idPrestamo, int idLibro) {
        String sql = "UPDATE Prestamo SET devuelto = true WHERE id_prestamo = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPrestamo);
            ps.executeUpdate();

            PreparedStatement psLibro = con.prepareStatement("UPDATE Libro SET disponible = true WHERE id_libro = ?");
            psLibro.setInt(1, idLibro);
            psLibro.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
