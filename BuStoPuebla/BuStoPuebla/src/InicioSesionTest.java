import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Method;

public class InicioSesionTest {
    private InicioSesion inicioSesion;

    @Before
    public void setUp() {
        inicioSesion = new InicioSesion();
    }

    @Test
    public void testAdministradorLoginExitoso() throws Exception {
        setTextField("usuarioField", "admin@gmail.com");
        setPasswordField("passwordField", "1234");
        setTipoUsuario("Administrador");
        assertTrue(invokeVerificarCredenciales());
    }

    @Test
    public void testEstudianteLoginFallido() throws Exception {
        setTextField("usuarioField", "estudiante@gmail.com");
        setPasswordField("passwordField", "wrongpass");
        setTipoUsuario("Estudiante");
        assertFalse(invokeVerificarCredenciales());
    }

    private void setTextField(String fieldName, String value) throws Exception {
        var field = InicioSesion.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        ((javax.swing.JTextField) field.get(inicioSesion)).setText(value);
    }

    private void setPasswordField(String fieldName, String value) throws Exception {
        var field = InicioSesion.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        ((javax.swing.JPasswordField) field.get(inicioSesion)).setText(value);
    }

    private void setTipoUsuario(String tipo) throws Exception {
        var field = InicioSesion.class.getDeclaredField("tipoUsuario");
        field.setAccessible(true);
        field.set(inicioSesion, tipo);
    }

    private boolean invokeVerificarCredenciales() throws Exception {
        Method method = InicioSesion.class.getDeclaredMethod("verificarCredenciales");
        method.setAccessible(true);
        return (boolean) method.invoke(inicioSesion);
    }
}