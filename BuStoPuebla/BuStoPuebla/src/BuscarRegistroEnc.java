import java.awt.event.*;
import java.security.MessageDigest;
import java.sql.*;
import javax.swing.*;

/**
 * Clase que permite buscar registros en la base de datos SQLite
 * usando SHA-256 para comparar ID encriptado.
 */
public class BuscarRegistroEnc implements ActionListener {
    private JTextField[] campos;

    public BuscarRegistroEnc(JTextField[] campos) {
        this.campos = campos;
    }

    /**
     * Encripta el texto usando el algoritmo SHA-256.
     * @param texto El texto a convertir.
     * @return Cadena en hexadecimal con el hash del texto.
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
            return texto;
        }
    }

    /**
     * Acción que se ejecuta al presionar el botón de búsqueda.
     * Busca por ID encriptado.
     */
    @Override
    public void actionPerformed(ActionEvent evento) {
        String numero = campos[2].getText();

        if (numero.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el ID el autobus para buscar.");
            return;
        }

        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db")) {
            // Usar SHA-256 para buscar los campos encriptados
            String sql = "SELECT * FROM addresses WHERE busnumber = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setString(1, encriptarSHA256(numero));

                ResultSet resultado = stmt.executeQuery();

                if (resultado.next()) {
                    campos[0].setText(resultado.getString("firstname")); // first name
                    campos[1].setText(resultado.getString("lastname")); // last name
                    campos[3].setText(resultado.getString("license"));
                    campos[4].setText(resultado.getString("route"));
                    campos[5].setText(resultado.getString("plate"));
                    campos[6].setText(resultado.getString("schedule"));
                    campos[7].setText(resultado.getString("toll"));
                    campos[8].setText(resultado.getString("salary"));
                    campos[9].setText(resultado.getString("complaints"));
                    JOptionPane.showMessageDialog(null, "✅ Registro encontrado.");
                } else {
                    JOptionPane.showMessageDialog(null, "⚠️ No se encontró ningún registro con ese ID.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al buscar registro: " + e.getMessage());
        }
    }
}
