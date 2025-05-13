import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JFrame;

public class AgregarRegistroTest {

    private static final String DB_URL = "jdbc:sqlite:RegistroUsuarios.db";
    private static final String SQL_SELECT_ALL = "SELECT * FROM usuarios";
    private static final String SQL_DELETE_ALL = "DELETE FROM usuarios";

    @Before
    public void setUp() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_ALL)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("No se pudo limpiar la base antes del test: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_ALL)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("No se pudo limpiar la base después del test: " + e.getMessage());
        }
    }

    @Test
    public void testAgregarRegistroValido() {
        String[] datos = {"cliente", "Santiago", "dos", "santy@gmail.com", "password123"};
        JTextField[] campos = new JTextField[datos.length];
        for (int i = 0; i < datos.length; i++) {
            campos[i] = new JTextField(datos[i]);
        }

        JFrame frame = new JFrame();
        new AgregarRegistro(campos, frame).actionPerformed(null);

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            boolean encontrado = false;
            while (rs.next()) {
                if (rs.getString("correo").equals("santy@gmail.com")) {
                    encontrado = true;
                    break;
                }
            }
            assertTrue("No se insertó el registro válido", encontrado);
        } catch (SQLException e) {
            fail("Error al verificar el registro válido: " + e.getMessage());
        }
    }

    @Test
    public void testAgregarRegistroConCamposVacios() {
        String[] vacios = {"", "", "", "", ""};
        JTextField[] campos = new JTextField[vacios.length];
        for (int i = 0; i < vacios.length; i++) {
            campos[i] = new JTextField(vacios[i]);
        }

        JFrame frame = new JFrame();
        new AgregarRegistro(campos, frame).actionPerformed(null); // no debe insertar

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            boolean hayRegistros = rs.next();
            assertFalse("Se insertó un registro con campos vacíos", hayRegistros);

        } catch (SQLException e) {
            fail("Error al verificar campos vacíos: " + e.getMessage());
        }
    }

    @Test
    public void testAgregarRegistroConCorreoDuplicado() {
        String[] datos1 = {"cliente", "Santiago", "uno", "alexis@gmail.com", "clave123"};
        String[] datos2 = {"cliente", "Alexis", "dos", "alexis@gmail.com", "otraClave"};

        JTextField[] campos1 = new JTextField[datos1.length];
        JTextField[] campos2 = new JTextField[datos2.length];
        for (int i = 0; i < datos1.length; i++) {
            campos1[i] = new JTextField(datos1[i]);
            campos2[i] = new JTextField(datos2[i]);
        }

        JFrame frame = new JFrame();
        new AgregarRegistro(campos1, frame).actionPerformed(null); // válido
        new AgregarRegistro(campos2, frame).actionPerformed(null); // duplicado → ignorado

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE correo = ?")) {

            stmt.setString(1, "alexis@gmail.com");
            ResultSet rs = stmt.executeQuery();

            int count = rs.next() ? rs.getInt(1) : 0;
            assertEquals("Se permitió más de un registro con el mismo correo", 1, count);

        } catch (SQLException e) {
            fail("Error al verificar correo duplicado: " + e.getMessage());
        }
    }

    @Test
    public void testAgregarRegistroConContrasenaEncriptada() {
        String[] datos = {"cliente", "Santiago", "dos", "santy@gmail.com", "password123"};
        JTextField[] campos = new JTextField[datos.length];
        for (int i = 0; i < datos.length; i++) {
            campos[i] = new JTextField(datos[i]);
        }

        JFrame frame = new JFrame();
        new AgregarRegistro(campos, frame).actionPerformed(null);

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            boolean encontrado = false;
            while (rs.next()) {
                if (rs.getString("correo").equals("santy@gmail.com")) {
                    encontrado = true;
                    String guardada = rs.getString("contraseña");
                    assertNotEquals("La contraseña fue guardada sin encriptar", "password123", guardada);
                    break;
                }
            }
            assertTrue("No se encontró el registro para verificar la contraseña", encontrado);
        } catch (SQLException e) {
            fail("Error al verificar contraseña: " + e.getMessage());
        }
    }
}
