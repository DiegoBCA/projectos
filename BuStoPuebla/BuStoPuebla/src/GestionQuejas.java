import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionQuejas {
    private static final String DB_URL = "jdbc:sqlite:bin/Quejas.db";
    public static void insertarQueja(String usuario, String queja) {
        String sql = "INSERT INTO Quejas(usuario, queja) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, queja);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar queja: " + e.getMessage());
        }
    }

    public static List<String> obtenerQuejas() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT usuario, queja, fecha FROM Quejas ORDER BY fecha DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String mensaje = rs.getString("fecha") + " - " +
                                 rs.getString("usuario") + ": " +
                                 rs.getString("queja");
                lista.add(mensaje);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener quejas: " + e.getMessage());
        }
        return lista;
    }
}
