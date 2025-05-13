import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GestionQuejasTest {
    private static final String DB_URL = "jdbc:sqlite:Quejas.db";
    private static final String SQL_SELECT_ALL = "SELECT * FROM Quejas";
    private static final String SQL_DELETE_ALL = "DELETE FROM Quejas";

    @Before
    public void setUp() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_ALL)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("Error al limpiar la base de datos antes del test: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_ALL)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("Error al limpiar la base de datos después del test: " + e.getMessage());
        }
    }

    @Test
    public void testInsertarQuejaValida() {
        String usuario = "Juan";
        String queja = "Prueba de inserción válida";
        GestionQuejas.insertarQueja(usuario, queja);

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            boolean encontrado = false;
            while (rs.next()) {
                if (rs.getString("usuario").equals(usuario)
                    && rs.getString("queja").equals(queja)) {
                    encontrado = true;
                    break;
                }
            }
            assertTrue("No se encontró la queja insertada en la base de datos.", encontrado);
        } catch (SQLException e) {
            fail("Error al consultar la base de datos: " + e.getMessage());
        }
    }

    @Test
    public void testObtenerQuejasFormateadas() {
        GestionQuejas.insertarQueja("Ana", "Primera queja");
        GestionQuejas.insertarQueja("Luis", "Segunda queja");

        List<String> lista = GestionQuejas.obtenerQuejas();
        assertEquals("Debe devolver 2 quejas almacenadas.", 2, lista.size());
        assertTrue("La primera entrada debe corresponder a Luis.", lista.get(0).contains("Luis: Segunda queja"));
        assertTrue("La segunda entrada debe corresponder a Ana.", lista.get(1).contains("Ana: Primera queja"));
    }

    @Test
    public void testInsertarQuejaConCamposVacios() {
        GestionQuejas.insertarQueja("", "");

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            boolean encontrado = false;
            while (rs.next()) {
                if (rs.getString("usuario").isEmpty()
                    && rs.getString("queja").isEmpty()) {
                    encontrado = true;
                    break;
                }
            }
            assertTrue("No se insertó el registro con campos vacíos.", encontrado);
        } catch (SQLException e) {
            fail("Error al consultar la base de datos: " + e.getMessage());
        }
    }
}
