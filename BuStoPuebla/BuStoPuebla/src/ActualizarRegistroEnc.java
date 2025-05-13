import java.awt.event.*;
import java.security.MessageDigest;
import java.sql.*;
import javax.swing.*;

/**
 * Clase que permite actualizar registros en la base de datos SQLite.
 * Utiliza SHA-256 para comparar ID encriptado.
 */
public class ActualizarRegistroEnc implements ActionListener {
    private JTextField[] campos;

    public ActualizarRegistroEnc(JTextField[] campos) {
        this.campos = campos;
    }

    /**
     * Encripta una cadena de texto utilizando SHA-256.
     * @param texto Texto plano.
     * @return Hash en formato hexadecimal.
     */
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
            return texto; // fallback no recomendado en producción
        }
    }

    /**
     * Acción ejecutada cuando se presiona el botón "Actualizar".
     * Busca por ID encriptado y actualiza los datos.
     */
    @Override
    public void actionPerformed(ActionEvent evento) {
        String numero = campos[2].getText();

        if (numero.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el ID del autobus para actualizar.");
            return;
        }

        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db")) {
            // SQL para actualizar los campos restantes
            String sql = "UPDATE addresses SET "
                       + "firstname = ?, "
                       + "lastname = ?, "
                       + "license = ?, "
                       + "route = ?, "
                       + "plate = ?, "
                       + "schedule = ?, "
                       + "toll = ?, "
                       + "salary = ?, "
                       + "complaints = ? "
                       + "WHERE busnumber = ?";

            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                // Setear los campos que serán actualizados
                stmt.setString(1, campos[0].getText()); // firstname
                stmt.setString(2, campos[1].getText()); // lastname
                for (int i = 3; i < campos.length; i++) {
                    stmt.setString(i, campos[i].getText()); // license en adelante
                }
                // Criterios de búsqueda: ID encriptado
                stmt.setString(10, encriptarSHA256(numero));

                int filasActualizadas = stmt.executeUpdate();
                if (filasActualizadas > 0) {
                    JOptionPane.showMessageDialog(null, "Registro actualizado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el registro a actualizar.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar registro: " + e.getMessage());
        }
    }
}
