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
    //Agregamos el método para encriptar la contraseña
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
                    JOptionPane.showMessageDialog(null, "Registro agregado.");
                    registroFrame.dispose();
                    new InicioSesion(registroFrame);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo agregar el registro.");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error SQL: " + ex.getMessage());
        }
    }
}
