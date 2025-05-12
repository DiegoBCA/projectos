

import java.awt.event.*;
import java.security.MessageDigest;
import java.sql.*;
import javax.swing.*;

public class BuscarRegistro implements ActionListener {
    private JTextField[] campos;
    private JFrame framePrincipal;
    private JLabel mensajeLabel;  // JLabel para testeo y mensajes

    public BuscarRegistro(JTextField[] campos, JFrame framePrincipal) {
        this.campos = campos;
        this.framePrincipal = framePrincipal;
        this.mensajeLabel = new JLabel(""); // Inicializado vacío
    }


    //Agregamos el método para encriptar la contraseña
    public String encriptarSHA256(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(texto.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            String mensaje=hexString.toString();
            mensajeLabel.setText(mensaje);

            return hexString.toString();
           
        } catch (Exception ex) {
            return texto;
        }
    }


    public boolean camposVacios(String correo, String  cont){
        if (correo.isEmpty()||cont.isEmpty()){
            String mensaje = "Debe ingresar correo y contraseña.";
            JOptionPane.showMessageDialog(null, mensaje);
            return true;
        }
        return false;

    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        String correo = campos[3].getText();
        String contraseña = campos[4].getText();

        // Validar que los campos no estén vacíos
        if (!camposVacios(correo, contraseña))
        {

            //Validamos si existe el correo y contraseña
            try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:RegistroUsuarios.db")) {
                //Necesitamos consultar el tipo de usuario
                String sql = "SELECT tipousuario FROM usuarios WHERE correo = ? AND contraseña = ?";
                try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                    stmt.setString(1, correo);
                    stmt.setString(2, encriptarSHA256(contraseña));

                    ResultSet resultado = stmt.executeQuery();
                    //Abrimos la ventana según el tipo de usuario
                    if (resultado.next()) {
                        String tipoUsuario = resultado.getString("tipousuario");
                        String mensaje = "Bienvenido " + tipoUsuario;
                        JOptionPane.showMessageDialog(null, mensaje);
                        framePrincipal.dispose(); // Cerrar ventana de login

                        // Crear y mostrar la ventana según el tipo de usuario
                        JFrame ventana = new JFrame();
                        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        ventana.setSize(600, 450); // Tamaño por defecto, se ajustará según el tipo de usuario
                        ventana.setLocationRelativeTo(null); // Centrar la ventana

                        switch (tipoUsuario.toLowerCase()) {
                            case "administrador":
                                ventana.setTitle("Administrador");
                                ventana.setContentPane(new IAdministrador());
                                break;
                            case "cliente":
                                ventana.setTitle("Cliente");
                                ventana.setContentPane(new ICliente());
                                break;
                            case "conductor":
                                ventana.setTitle("Conductor");
                                ventana.setContentPane(new IConductor());
                                break;
                            default:
                                String desconocido = "Tipo de usuario desconocido.";
                                JOptionPane.showMessageDialog(null, desconocido);
                                return;
                        }

                        ventana.setVisible(true); // Mostrar la ventana
                    } else {
                        String mensaje = "Correo o contraseña incorrectos.";
                        JOptionPane.showMessageDialog(null, mensaje);
                    }
                }
            } catch (SQLException e) {
                String mensaje = "Error al buscar registro: " + e.getMessage();
                JOptionPane.showMessageDialog(null, mensaje);
            }
        }
    }
}