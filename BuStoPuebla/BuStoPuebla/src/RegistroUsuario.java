import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

class RegistroUsuario extends JPanel {
    private final JTextField nombreField,apellidosField,correoField;
    private final JPasswordField contraseñaField;
    private final JRadioButton[] radio;
    private final ButtonGroup grupoUsuarios;
    private final String[] radioEtiquetas = { "Administrador", "Cliente", "Conductor" };
    //Damos forma a la ventana de registro de usuario
    public RegistroUsuario(JFrame frameRegistro) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nombre:"),gbc);
        gbc.gridx = 1;
        nombreField = new JTextField(25);
        add(nombreField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Apellidos:"),gbc);
        gbc.gridx = 1;
        apellidosField = new JTextField(25);
        add(apellidosField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Correo Electrónico:"),gbc);
        gbc.gridx = 1;
        correoField = new JTextField(25);
        add(correoField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Contraseña:"),gbc);
        contraseñaField = new JPasswordField(25);
        gbc.gridx = 1;
        add(contraseñaField,gbc);
        // Botón para agregar el registro
       
        
        JButton botonAgregar = new JButton("Agregar");
        gbc.gridx = 0;
        gbc.gridy = 4;
       
        add(new JLabel("Tipo de Usuario:"),gbc);
        JPanel panelRadios = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radio = new JRadioButton[radioEtiquetas.length];
        grupoUsuarios = new ButtonGroup();

        for (int i = 0; i < radioEtiquetas.length; i++) {
            radio[i] = new JRadioButton(radioEtiquetas[i]);
            grupoUsuarios.add(radio[i]);
            panelRadios.add(radio[i]);
        }
        gbc.gridx = 1;
        add(panelRadios,gbc);
        botonAgregar.addActionListener(e -> {
            String tipoUsuario = null;
            for (JRadioButton rb : radio) {
                if (rb.isSelected()) {
                    tipoUsuario = rb.getText();
                    break;
                }
            }
            // Verificamos que todos los campos estén completos
            if (tipoUsuario == null || nombreField.getText().isEmpty() || apellidosField.getText().isEmpty()
                    || correoField.getText().isEmpty() || String.valueOf(contraseñaField.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.");
                return;
            }
            // Verificamos que el correo tenga un formato válido
            if (!correoField.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(this, "El correo electrónico no es válido.");
                return;
            }
            // Verificamos que la contraseña tenga al menos 8 caracteres
            if (contraseñaField.getPassword().length < 8) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 8 caracteres.");
                return;
            }
            // Verificamos que el correo no esté ya registrado
            
            try (Connection conexion = ConexionSQLite.conectar()) {
                String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
                try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                    stmt.setString(1, correoField.getText());
                    ResultSet resultado = stmt.executeQuery();
                    if (resultado.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(this, "El correo ya está registrado.");
                        return;
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al verificar el correo: " + ex.getMessage());
                return;
            }
            JTextField[] campos = {
                new JTextField(tipoUsuario),
                nombreField,
                apellidosField,
                correoField,
                new JTextField(String.valueOf(contraseñaField.getPassword()))
            };

            new AgregarRegistro(campos, frameRegistro).actionPerformed(null);
            frameRegistro.dispose();

            // Volvemos a abrir la ventana de iniciar sesión para ingresar con lo registrado 
            JFrame frameLogin = new JFrame("Inicio de Sesión");
            frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameLogin.setSize(400, 250);
            frameLogin.setLocationRelativeTo(null);
            frameLogin.setContentPane(new InicioSesion(frameLogin));
            frameLogin.setVisible(true);
        });
        add(botonAgregar,gbc);
        JButton botonRegresar=new JButton("Regresar");
        botonRegresar.addActionListener(e -> {
            frameRegistro.dispose();
            JFrame frameLogin = new JFrame("Inicio de Sesión");
            frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameLogin.setSize(300, 200);
            frameLogin.setLocationRelativeTo(null);
            frameLogin.setContentPane(new InicioSesion(frameLogin));
            frameLogin.setVisible(true);
        });
        add(botonRegresar,gbc);
    }
}
