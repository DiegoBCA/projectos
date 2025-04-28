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
        System.out.println("test inicado");
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_ALL)) {
            stmt.executeUpdate();
            System.out.println("Se elimino todo lo de al base");
        } catch (SQLException e) {
            System.err.println("Error setting up the test: " + e.getMessage());
            fail("Error setting up the test: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        System.out.println("test finalizado");
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_ALL)) {
            stmt.executeUpdate();
            System.out.println("Base de datos limpia");
        } catch (SQLException e) {
            System.err.println("Error al terminar el test" + e.getMessage());
            fail("Error al terminar el test" + e.getMessage());
        }
    }

    @Test
    public void testAgregarRegistroValido() {
        System.out.println("Test registro valido");
        // Test data
        String[] testData = {
            "user",
            "Santiago",
            "dos",
            "santy@gmail.com",
            "password123"
        };

        JTextField[] campos = new JTextField[testData.length];
        for (int i = 0; i < testData.length; i++) {
            campos[i] = new JTextField(testData[i]);
        }

        // Create an instance of JFrame if needed
        JFrame registroFrame = new JFrame();
        AgregarRegistro agregarRegistro = new AgregarRegistro(campos, registroFrame);
        agregarRegistro.actionPerformed(null);

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            boolean found = false;
            while (rs.next()) {
                if (rs.getString("correo").equals("santy@gmail.com")) {
                    found = true;
                    break;
                }
            }
            assertTrue("no se encontro en a base", found);
            System.out.println("Test completado.");
        } catch (SQLException e) {
            System.err.println("Error no se completo el test " + e.getMessage());
            fail("Error no se completo el test " + e.getMessage());
        }
    }

    @Test
    public void testAgregarRegistroConCamposVacios() {
        System.out.println("test registro vacio");
        String[] testData = {
            "",
            "",
            "",
            "",
            ""
        };

        JTextField[] campos = new JTextField[testData.length];
        for (int i = 0; i < testData.length; i++) {
            campos[i] = new JTextField(testData[i]);
        }

        // Create an instance of JFrame if needed
        JFrame registroFrame = new JFrame();
        AgregarRegistro agregarRegistro = new AgregarRegistro(campos, registroFrame);
        agregarRegistro.actionPerformed(null);

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            boolean found = false;
            while (rs.next()) {
                if (rs.getString("correo").equals("")) {
                    found = true;
                    break;
                }
            }
            assertFalse("Record with empty fields found in database", found);
            System.out.println("Test passed successfully.");
        } catch (SQLException e) {
            System.err.println("Error verifying record: " + e.getMessage());
            fail("Error verifying record: " + e.getMessage());
        }
    }

    @Test
    public void testAgregarRegistroConCorreoDuplicado() {
        System.out.println("Running testAgregarRegistroConCorreoDuplicado...");
        // Test data
        String[] testData1 = {
            "user",
            "John",
            "Doe",
            "john.doe@example.com",
            "password123"
        };

        String[] testData2 = {
            "user",
            "Jane",
            "Doe",
            "john.doe@example.com",
            "password456"
        };

        JTextField[] campos1 = new JTextField[testData1.length];
        for (int i = 0; i < testData1.length; i++) {
            campos1[i] = new JTextField(testData1[i]);
        }

        JTextField[] campos2 = new JTextField[testData2.length];
        for (int i = 0; i < testData2.length; i++) {
            campos2[i] = new JTextField(testData2[i]);
        }

        // Create an instance of JFrame if needed
        JFrame registroFrame = new JFrame();
        AgregarRegistro agregarRegistro1 = new AgregarRegistro(campos1, registroFrame);
        agregarRegistro1.actionPerformed(null);

        AgregarRegistro agregarRegistro2 = new AgregarRegistro(campos2, registroFrame);
        agregarRegistro2.actionPerformed(null);

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            int count = 0;
            while (rs.next()) {
                if (rs.getString("correo").equals("john.doe@example.com")) {
                    count++;
                }
            }
            assertEquals("Duplicate email found in database", 2, count);
            System.out.println("Test passed successfully.");
        } catch (SQLException e) {
            System.err.println("Error verifying record: " + e.getMessage());
            fail("Error verifying record: " + e.getMessage());
        }
    }

    @Test
    public void testAgregarRegistroConContrasenaEncriptada() {
        System.out.println("Running testAgregarRegistroConContrasenaEncriptada...");
        // Test data
        String[] testData = {
            "user",
            "John",
            "Doe",
            "john.doe@example.com",
            "password123"
        };

        JTextField[] campos = new JTextField[testData.length];
        for (int i = 0; i < testData.length; i++) {
            campos[i] = new JTextField(testData[i]);
        }

        // Create an instance of JFrame if needed
        JFrame registroFrame = new JFrame();
        AgregarRegistro agregarRegistro = new AgregarRegistro(campos, registroFrame);
        agregarRegistro.actionPerformed(null);

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            boolean found = false;
            while (rs.next()) {
                if (rs.getString("correo").equals("john.doe@example.com")) {
                    found = true;
                    String contrasenaEncriptada = rs.getString("contraseña");
                    assertNotEquals("Password is not encrypted", "password123", contrasenaEncriptada);
                    break;
                }
            }
            assertTrue("Record not found in database", found);
            System.out.println("Test passed successfully.");
        } catch (SQLException e) {
            System.err.println("Error verifying record: " + e.getMessage());
            fail("Error verifying record: " + e.getMessage());
        }
    }
}
