import java.util.ArrayList;
import java.util.List;

// Agregar esta parte en InicioSesion
public class InicioSesion extends JFrame implements ActionListener {
    private static List<String[]> usuariosRegistrados = new ArrayList<>();

    // Método para agregar un usuario a la lista
    public static void agregarUsuario(String tipo, String correo, String password) {
        usuariosRegistrados.add(new String[]{tipo, correo, password});
    }

    private void verificarCredenciales() {
        String usuario = usuarioField.getText();
        String password = new String(passwordField.getPassword());

        if (tipoUsuario == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un tipo de usuario", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar en credenciales predefinidas
        String[][] credenciales = {
            {"Administrador", "admin@gmail.com", "1234"},
            {"Administrador", "administrador@gmail.com", "12345"},
            {"Administrador", "administrativo@gmail.com", "123456"},
            {"Estudiante", "estudiante@gmail.com", "1234"},
            {"Estudiante", "estudiante1@gmail.com", "12345"},
            {"Estudiante", "estudiante2@gmail.com", "123456"},
            {"Conductor", "conductor@gmail.com", "1234"},
            {"Conductor", "conductor1@gmail.com", "12345"},
            {"Conductor", "conductor2@gmail.com", "123456"}
        };

        for (String[] credencial : credenciales) {
            if (credencial[0].equals(tipoUsuario) && credencial[1].equals(usuario) && credencial[2].equals(password)) {
                JOptionPane.showMessageDialog(this, "Bienvenido " + tipoUsuario, "Inicio de Sesión", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        // Verificar en usuarios registrados
        for (String[] credencial : usuariosRegistrados) {
            if (credencial[0].equals(tipoUsuario) && credencial[1].equals(usuario) && credencial[2].equals(password)) {
                JOptionPane.showMessageDialog(this, "Bienvenido " + tipoUsuario, "Inicio de Sesión", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
