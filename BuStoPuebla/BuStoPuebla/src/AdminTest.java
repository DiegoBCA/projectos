import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }
    }

    @Test
    public void buscarCampoInexistente() {
        campos[2].setText("aaaaa");
        try {
            buscarRegistro.actionPerformed(null);
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }
    }

    @Test
    public void buscarCampoExistente() {
        String id = "999";
        campos[2].setText(id);
        buscarRegistro.actionPerformed(null);
        assertEquals("999", id);
        assertNotEquals("", id);
        for(int i = 3; i < 10; i++){
            assertEquals("", campos[i].getText());
        }
    }
    @Test
    public void buscarCampoEnc() {
        campos[0].setText("Juan");
        campos[1].setText("López");  
        campos[2].setText("185008");
        campos[3].setText("licencia1"); 
        campos[4].setText("Ruta1");
        campos[5].setText("Placa1");
        campos[6].setText("9:00-12:00");
        campos[7].setText("$10");
        campos[8].setText("$210/d");
        campos[9].setText("-");
        for(int i = 0; i < 10; i++){
            campos[i].setText(buscarRegistro.encriptarSHA256(campos[i].getText()));
        }
        String nombreEnc = "cb80be76d732c36bd5f71ecdd7b6964556730a19ceccd8b8c1869220bb4c7b7c";
        String apellidoEnc = "30070e1094641fc5a0fe60121e629b6371348f7f655c96cc57b255bd136a3630";
        String idEnc = "61d57fb4ae4c84278e1d1b7cde2bc76c6006d4ccddbac7287304b758e06285ba";
        String licEnc = "1026539a3a6c861309fe3b483de72c26bf4152b411bdf1847fc11b82be5e2031";
        String rutaEnc = "54c89a6b68349980fe4f47c140b388aab630e0dbd9ba09e7ce07399b7b937890";
        String placaEnc = "208565a7b86c0b0baacf553c6444d7c1f9d65e3cdac5585aab8e4bdd211ff765";
        String horEnc = "a565ec694434daa80179935310078e4e892376b567240cf5dc628cd11efb38b4";
        String peajeEnc = "a5e5e8d362fb74d764de0ae02c1301975aa694ee1175c61c2cc45a60a78245ec";
        String salarioEnc = "7318c95db9081028c2b6503c08003602ced23380a3c043d81ad8e6ed7d7a4007";
        String quejaEnc = "3973e022e93220f9212c18d0d0c543ae7c309e46640da93a4a0314de999f5112";
        
        assertEquals(campos[0].getText(), nombreEnc);
        assertEquals(campos[1].getText(), apellidoEnc);
        assertEquals(campos[2].getText(), idEnc);
        assertEquals(campos[3].getText(), licEnc);
        assertEquals(campos[4].getText(), rutaEnc);
        assertEquals(campos[5].getText(), placaEnc);
        assertEquals(campos[6].getText(), horEnc);
        assertEquals(campos[7].getText(), peajeEnc);
        assertEquals(campos[8].getText(), salarioEnc);
        assertEquals(campos[9].getText(), quejaEnc);
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
    public void agregarCampoVacio() throws SQLException{
        campos[2].setText(""); 
        try {
            agregarRegistro.actionPerformed(null);
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }
    }
    @Test
    public void agregarValorCampos() throws SQLException{
        campos[0].setText("Juan");
        campos[1].setText("López");  
        campos[2].setText("1234");
        campos[3].setText("licencia1"); 
        campos[4].setText("Ruta1");
        campos[5].setText("Placa1");
        campos[6].setText("9:00-12:00");
        campos[7].setText("$10");
        campos[8].setText("$210/d");
        campos[9].setText("-");   
        try {
            agregarRegistro.actionPerformed(null);
            assertEquals("Juan", campos[0].getText());
            assertEquals("López", campos[1].getText());
            assertEquals("1234", campos[2].getText());
            assertEquals("licencia1", campos[3].getText());
            assertEquals("Ruta1", campos[4].getText());
            assertEquals("Placa1", campos[5].getText());
            assertEquals("9:00-12:00", campos[6].getText());
            assertEquals("$10", campos[7].getText());
            assertEquals("$210/d", campos[8].getText());
            assertEquals("-", campos[9].getText());
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }
    }
    @Test
    public void idRepetido() {
        campos[0].setText("Juan");
        campos[1].setText("López");  
        campos[2].setText("IDREPETIDO");
        campos[3].setText("licencia1"); 
        campos[4].setText("Ruta1");
        campos[5].setText("Placa1");
        campos[6].setText("9:00-12:00");
        campos[7].setText("$10");
        campos[8].setText("$210/d");
        campos[9].setText("-"); 
        try {
            agregarRegistro.actionPerformed(null);
        } catch (Exception e) {
            fail("No se esperaba ninguna excepción, pero ocurrió: " + e.getMessage());
        }try {
            agregarRegistro.actionPerformed(null);
        } catch (Exception e) {
            fail("ID repetido: " + e.getMessage());
        }
    }

    @Test
    public void limpiarCampos(){
        campos[0].setText("Juan");
        campos[1].setText("López");  
        campos[2].setText("1234");
        campos[3].setText("licencia1"); 
        campos[4].setText("Ruta1");
        campos[5].setText("Placa1");
        campos[6].setText("9:00-12:00");
        campos[7].setText("$10");
        campos[8].setText("$210/d");
        campos[9].setText("-");
        limpiarCampos.actionPerformed(null);
        for(int i = 0; i < 10; i++){
            assertEquals("", campos[i].getText());
        }
    }
}
