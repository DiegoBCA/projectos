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
            System.err.println("Error no se conecto con la base " + e.getMessage());
            fail("Error no se conecto con la base " + e.getMessage());
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
            //assertFalse("no se encontro en la base de datos",found);
            //System.out.println("Se completo el test.");
        } catch (SQLException e) {
            System.err.println("Error no funciono el test " + e.getMessage());
            fail("Error no funciono el test " + e.getMessage());
        }
    }

    @Test
    public void testAgregarRegistroConCorreoDuplicado() {
        System.out.println("Test correo duplicado");
       
        String[] testData1 = {
            "user",
            "Santiago",
            "dos",
            "santy@gmail.com",
            "password123"
        };

        String[] testData2 = {
            "user",
            "alexis",
            "uno",
            "alexis@gmail.com",
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
                if (rs.getString("correo").equals("alexis@gmail.com")) {
                    count++;
                }
            }
            assertEquals("email duplicado", 1, count);
            System.out.println("Se completo el test.");
        } catch (SQLException e) {
            System.err.println("Error no se termino el test " + e.getMessage());
            fail("Error no funciono el test " + e.getMessage());
        }
    }

    @Test
    public void testAgregarRegistroConContrasenaEncriptada() {
        System.out.println("Test contraseña encriptada");
      
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
                    String contrasenaEncriptada = rs.getString("contraseña");
                    assertNotEquals("contraseña no encryptada", "password123", contrasenaEncriptada);
                    break;
                }
            }
            assertTrue("No se encontro en la base", found);
            System.out.println("Se completo el test.");
        } catch (SQLException e) {
            System.err.println("Error no funciono el test " + e.getMessage());
            fail("Error no funciono el test " + e.getMessage());
        }
    }
}
