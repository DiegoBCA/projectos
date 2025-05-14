import java.awt.event.*;
import java.security.MessageDigest;
import java.sql.*;
import javax.swing.*;

public class AgregarRegistro implements ActionListener {
    private JTextField[] campos;
    private JFrame registroFrame;

    public AgregarRegistro(JTextField[] campos, JFrame registroFrame) {
        this.campos = campos;
        this.registroFrame = registroFrame;
    }

    // Método para encriptar contraseña con SHA-256
    private String encriptarSHA256(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(texto.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            return texto;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try (Connection conexion = ConexionSQLite.conectar()) {

            for (JTextField campo : campos) {
                if (campo.getText().trim().isEmpty()) {
                    System.out.println("Campo vacío detectado");
                    return;
                }
            }
            

            // Validar si el correo ya existe
            String correo = campos[3].getText().trim();
            String checkCorreo = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
            try (PreparedStatement checkStmt = conexion.prepareStatement(checkCorreo)) {
                checkStmt.setString(1, correo);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Correo duplicado detectado");
                    return; // no insertamos duplicados
                }
            }

            // Insertar usuario
            String sql = "INSERT INTO usuarios (tipousuario, nombre, apellidos, correo, contraseña) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                for (int i = 0; i < campos.length; i++) {
                    if (i == 4) {
                        stmt.setString(i + 1, encriptarSHA256(campos[i].getText()));
                    } else {
                        stmt.setString(i + 1, campos[i].getText());
                    }
                }

                int filasInsertadas = stmt.executeUpdate();
                if (filasInsertadas > 0) {
                    System.out.println("Registro exitoso");
                    registroFrame.dispose();
                    new InicioSesion(registroFrame); // solo si ya lo tienes definido
                } else {
                    System.out.println("No se pudo insertar");
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
        }
    }
}
