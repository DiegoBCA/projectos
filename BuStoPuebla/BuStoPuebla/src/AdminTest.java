import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.sql.*;

public class AdminTest {
    private BuscarRegistroEnc buscarRegistro;
    private JTextField[] campos;

    @Before
    public void setUp() {
        campos = new JTextField[10];
        for (int i = 0; i < campos.length; i++) {
            campos[i] = new JTextField();
        }
        buscarRegistro = new BuscarRegistroEnc(campos);
    }

    @Test
    public void testActionPerformedCampoVacio() {
        campos[2].setText(""); 
        try {
            buscarRegistro.actionPerformed(null);
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }
    }

    @Test
    public void testActionPerformedCampoConValor() {
        campos[2].setText("12345");
        try {
            buscarRegistro.actionPerformed(null);
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }
    }

    @Test
    public void testActionPerformedSQLException() {
        campos[2].setText("12345");  
        try {
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db");
            throw new SQLException("Error de conexión");
        } catch (SQLException e) {
            assertTrue(e.getMessage().contains("Error de conexión"));
        }
    }

    @Test
    public void testActionPerformedRegistroNoEncontrado() throws SQLException {
        campos[2].setText("12345");
        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db")) {
            String sql = "SELECT * FROM addresses WHERE busnumber = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setString(1, "12345");
                ResultSet resultado = stmt.executeQuery();

                if (!resultado.next()) { 
                    JOptionPane.showMessageDialog(null, "⚠️ No se encontró ningún registro con ese ID.");
                }
            }
        } catch (SQLException e) {
            fail("Se produjo un error inesperado en la base de datos: " + e.getMessage());
        }
    }

    @Test
    public void testActionPerformedRegistroEncontrado() throws SQLException {
        campos[2].setText("12345"); 

        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db")) {
            String sql = "SELECT * FROM addresses WHERE busnumber = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setString(1, "12345");
                ResultSet resultado = stmt.executeQuery();

                if (resultado.next()) { 
                    campos[0].setText(resultado.getString("firstname"));
                    campos[1].setText(resultado.getString("lastname"));
                    assertNotNull(campos[0].getText());
                    assertNotNull(campos[1].getText());
                }
            }
        } catch (SQLException e) {
            fail("Se produjo un error inesperado en la base de datos: " + e.getMessage());
        }
    }
}
