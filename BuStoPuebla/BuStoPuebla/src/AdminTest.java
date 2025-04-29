import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.sql.*;

public class AdminTest {
    private BuscarRegistroEnc buscarRegistro;
    private AgregarRegistroEnc agregarRegistro;
    private LimpiarCampos limpiarCampos;
    private JTextField[] campos;

    @Before
    public void setUp() {
        campos = new JTextField[10];
        for (int i = 0; i < campos.length; i++) {
            campos[i] = new JTextField();
        }
        buscarRegistro = new BuscarRegistroEnc(campos);
        agregarRegistro = new AgregarRegistroEnc(campos);
        limpiarCampos = new LimpiarCampos(campos);
    }

    @Test
    public void buscarCampoVacio() {
        campos[2].setText(""); 
        try {
            buscarRegistro.actionPerformed(null);
            assertEquals("", campos[2].getText());
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }
    }

    @Test
    public void buscarCampoValor() {
        campos[2].setText("12345");
        try {
            buscarRegistro.actionPerformed(null);
            assertEquals("12345", campos[2].getText());
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
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
    
    @Test
    public void agregarVacio() throws SQLException{
        campos[2].setText(""); 
        try {
            agregarRegistro.actionPerformed(null);
            assertEquals("", campos[2].getText());
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }
    }
    @Test
    public void agregarValor() throws SQLException{
        campos[2].setText("12345"); 
        try {
            agregarRegistro.actionPerformed(null);
            assertEquals("12345", campos[2].getText());
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }
    }

    @Test
    public void limpiar(){
        campos[2].setText("12345");
        limpiarCampos.actionPerformed(null);
        assertEquals("", campos[2].getText());
    }
}
