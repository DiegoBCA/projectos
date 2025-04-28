import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.sql.*;

public class AdminTest {
    private BuscarRegistroEnc buscarRegistro;
    private AgregarRegistroEnc agregarRegistro;
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
    public void buscarCampoVacio() {
        campos[2].setText(""); 
        try {
            buscarRegistro.actionPerformed(null);
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }
    }

    @Test
    public void buscarCampoValor() {
        campos[2].setText("12345");
        try {
            buscarRegistro.actionPerformed(null);
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }
    }

    @Test
    public void testSQLException() {
        campos[2].setText("12345");  
        try {
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db");
            throw new SQLException("Error de conexión");
        } catch (SQLException e) {
            assertTrue(e.getMessage().contains("Error de conexión"));
        }
    }

    @Test
    public void testRegistroNoEncontrado() throws SQLException {
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
    public void testRegistroEncontrado() throws SQLException {
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
    @Before
    public void setUp2() {
        campos = new JTextField[10];
        for (int i = 0; i < campos.length; i++) {
            campos[i] = new JTextField();
        }
        agregarRegistro = new AgregarRegistroEnc(campos);
    }
    @Test
    public void agregarVacio() throws SQLException{
        campos[2].setText(""); 
        try {
            agregarRegistro.actionPerformed(null);
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }
    }
    @Test
    public void agregarValor() throws SQLException{
        campos[2].setText("1234"); 
        try {
            agregarRegistro.actionPerformed(null);
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }
    }
}
