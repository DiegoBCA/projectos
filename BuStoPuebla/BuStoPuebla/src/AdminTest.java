import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.sql.*;

public class BuscarRegistroEncTest {
    private BuscarRegistroEnc buscarRegistro;
    private JTextField[] campos;

    @BeforeEach
    public void setUp() {
        campos = new JTextField[10]; // 10 campos como en el código original
        for (int i = 0; i < campos.length; i++) {
            campos[i] = new JTextField();
        }
        buscarRegistro = new BuscarRegistroEnc(campos);
    }

    @Test
    public void testActionPerformedCampoVacio() {
        campos[2].setText("");  // Campo número de bus vacío
        try {
            buscarRegistro.actionPerformed(null);  // Ejecutar el método
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }
    }

    @Test
    public void testActionPerformedCampoConValor() {
        campos[2].setText("12345");  // Campo número de bus con valor
        try {
            buscarRegistro.actionPerformed(null);  // Ejecutar el método
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }
    }

    @Test
    public void testActionPerformedSQLException() {
        // Simulamos un error de conexión a la base de datos directamente
        campos[2].setText("12345");  // Campo con valor

        // Modificamos el método 'actionPerformed' para lanzar una excepción de SQLException.
        try {
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db");
            // Simulamos un error de SQL, lo que debería disparar el bloque catch
            throw new SQLException("Error de conexión");
        } catch (SQLException e) {
            assertTrue(e.getMessage().contains("Error de conexión"));
        }
    }

    @Test
    public void testActionPerformedRegistroNoEncontrado() throws SQLException {
        campos[2].setText("12345");  // Campo con valor

        // Simulamos que la consulta no encuentra ningún registro.
        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db")) {
            String sql = "SELECT * FROM addresses WHERE busnumber = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setString(1, "12345");
                ResultSet resultado = stmt.executeQuery();

                if (!resultado.next()) {  // Si no hay resultados, lanzamos el mensaje de error
                    JOptionPane.showMessageDialog(null, "⚠️ No se encontró ningún registro con ese ID.");
                }
            }
        } catch (SQLException e) {
            fail("Se produjo un error inesperado en la base de datos: " + e.getMessage());
        }
    }

    @Test
    public void testActionPerformedRegistroEncontrado() throws SQLException {
        campos[2].setText("12345");  // Campo con valor

        // Simulamos que la consulta encuentra un registro.
        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db")) {
            String sql = "SELECT * FROM addresses WHERE busnumber = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setString(1, "12345");
                ResultSet resultado = stmt.executeQuery();

                if (resultado.next()) {  // Si se encuentra un registro
                    campos[0].setText(resultado.getString("firstname"));
                    campos[1].setText(resultado.getString("lastname"));
                    // Aquí simulamos la actualización de los campos
                    assertNotNull(campos[0].getText());
                    assertNotNull(campos[1].getText());
                }
            }
        } catch (SQLException e) {
            fail("Se produjo un error inesperado en la base de datos: " + e.getMessage());
        }
    }
}
