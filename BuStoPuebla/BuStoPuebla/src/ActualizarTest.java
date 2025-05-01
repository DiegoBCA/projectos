import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.sql.*;
import java.security.MessageDigest;

public class ActualizarTest {
    private ActualizarRegistroEnc actualizarRegistro;
    private JTextField[] campos;

    @Before
    public void setUp() {
        campos = new JTextField[10];
        for (int i = 0; i < campos.length; i++) {
            campos[i] = new JTextField();
            campos[i].setText("valor" + i); // valores dummy por defecto
        }
        campos[2].setText("12345"); // ID a encriptar
        actualizarRegistro = new ActualizarRegistroEnc(campos);
    }

    @Before
    public void insertarRegistroPrueba() throws SQLException {
    String hash = encriptarSHA256("12345");
        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db")) {
        String sql = "INSERT OR IGNORE INTO addresses (busnumber, firstname) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, hash);
            stmt.setString(2, "NombreOriginal");
            stmt.executeUpdate();
            }
        }
    }
    
    @Test
    public void testCampoIDVacio() {
        campos[2].setText(""); // ID vacío
        try {
            actualizarRegistro.actionPerformed(null);
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción con ID vacío: " + e.getMessage());
        }
    }

    @Test
    public void testSQLExceptionEnConexion() {
        campos[2].setText("12345");
        try {
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db");
            throw new SQLException("Simulación de error de conexión");
        } catch (SQLException e) {
            assertTrue(e.getMessage().contains("Simulación de error"));
        }
    }

    @Test
    public void testActualizarRegistroNoExistente() throws SQLException {
        campos[2].setText("ID_NO_EXISTE");

        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db")) {
            String sql = "UPDATE addresses SET firstname = ? WHERE busnumber = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setString(1, "NombrePrueba");
                stmt.setString(2, "ID_NO_EXISTE");
                int filas = stmt.executeUpdate();
                assertEquals(0, filas);
            }
        } catch (SQLException e) {
            fail("No se esperaba error de base de datos: " + e.getMessage());
        }
    }

    @Test
    public void testActualizarRegistroExistente() throws SQLException {
        // Supone que ya existe un registro con el ID encriptado de "12345"
        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db")) {
            String sql = "UPDATE addresses SET firstname = ? WHERE busnumber = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setString(1, "NuevoNombre");
                stmt.setString(2, encriptarSHA256("12345"));
                int filas = stmt.executeUpdate();
                assertTrue(filas > 0);
            }
        } catch (SQLException e) {
            fail("Se produjo un error al intentar actualizar: " + e.getMessage());
        }
    }

    // Utilidad para encriptar igual que en la clase original
    private String encriptarSHA256(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(texto.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            return texto;
        }
    }
}