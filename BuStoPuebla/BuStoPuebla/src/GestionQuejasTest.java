import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.*;
import java.util.List;

public class GestionQuejasTest {

    private final String DB_URL = "jdbc:sqlite:Quejas.db";

    @Before
    public void setUp() throws Exception {
        // Limpiar la tabla antes de cada prueba
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM Quejas");
        }
    }

    @After
    public void tearDown() throws Exception {
        // Limpiar la tabla después de cada prueba
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM Quejas");
        }
    }

    @Test
    public void insertarQuejaValida() {
        GestionQuejas.insertarQueja("Carlos", "Ruta: Ruta 10 | Queja: El camión no pasó");
        List<String> quejas = GestionQuejas.obtenerQuejas();
        assertEquals(1, quejas.size());
        assertTrue(quejas.get(0).contains("Ruta: Ruta 10"));
    }

    @Test
    public void insertarQuejaVacia() {
        GestionQuejas.insertarQueja("", "");
        List<String> quejas = GestionQuejas.obtenerQuejas();
        assertEquals(1, quejas.size());  // Asumiendo que aún se guarda aunque esté vacía
        assertEquals("", quejas.get(0));
    }

    @Test
    public void obtenerQuejasConMultiplesEntradas() {
        GestionQuejas.insertarQueja("Ana", "Ruta: A | Queja: Muy sucia");
        GestionQuejas.insertarQueja("Luis", "Ruta: B | Queja: Muy tarde");
        List<String> quejas = GestionQuejas.obtenerQuejas();
        assertEquals(2, quejas.size());
    }

    @Test
    public void obtenerQuejasSinEntradas() throws SQLException {
        List<String> quejas = GestionQuejas.obtenerQuejas();
        assertNotNull(quejas);
        assertTrue(quejas.isEmpty());
    }

    @Test
    public void pruebaConexionBD() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            assertNotNull("La conexión no debe ser null", conn);
        } catch (SQLException e) {
            fail("No se pudo conectar a la base de datos: " + e.getMessage());
        }
    }

    @Test
    public void testArchivoBDExiste() {
        File archivo = new File("Quejas.db");
        assertTrue("El archivo Quejas.db debería existir", archivo.exists());
    }
}
