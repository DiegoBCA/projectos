import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class RegistroUsuario extends JFrame implements ActionListener {
    private String tipoUsuario;
    private JTextField nombreField, usuarioField;
    private JPasswordField passwordField, confirmarPasswordField;
    private JRadioButton administrador, estudiante, conductor;
    private JButton registroButton;

    public RegistroUsuario() {
        setTitle("Formulario de Registro");
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Formulario de Registro", SwingConstants.LEFT);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, gbc);

        gbc.gridy = 1;
        JPanel usuarioPanel = new JPanel();
        administrador = new JRadioButton("Administrador");
        estudiante = new JRadioButton("Estudiante");
        conductor = new JRadioButton("Conductor");
        ButtonGroup usuarioGroup = new ButtonGroup();
        usuarioGroup.add(administrador);
        usuarioGroup.add(estudiante);
        usuarioGroup.add(conductor);
        usuarioPanel.add(administrador);
        usuarioPanel.add(estudiante);
        usuarioPanel.add(conductor);
        add(usuarioPanel, gbc);

        administrador.addActionListener(this);
        estudiante.addActionListener(this);
        conductor.addActionListener(this);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(new JLabel("Nombre Completo:"), gbc);
        gbc.gridx = 1;
        nombreField = new JTextField(12);
        add(nombreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1;
        usuarioField = new JTextField(12);
        add(usuarioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(12);
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Confirmar Contraseña:"), gbc);
        gbc.gridx = 1;
        confirmarPasswordField = new JPasswordField(12);
        add(confirmarPasswordField, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        registroButton = new JButton("Registrar");
        registroButton.setBackground(Color.CYAN);
        add(registroButton, gbc);
        registroButton.addActionListener(this);

        setVisible(true);
    }

    private void registrarUsuario() {
        String nombre = nombreField.getText();
        String usuario = usuarioField.getText();
        String password = new String(passwordField.getPassword());
        String confirmarPassword = new String(confirmarPasswordField.getPassword());

        if (tipoUsuario == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un tipo de usuario", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nombre.isEmpty() || usuario.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmarPassword)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Aquí puedes agregar la lógica para guardar el usuario en una base de datos o un archivo
        JOptionPane.showMessageDialog(this, "Registro exitoso para " + tipoUsuario + ": " + nombre, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        this.dispose(); // Cierra la ventana de registro
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == administrador) {
            tipoUsuario = "Administrador";
        } else if (e.getSource() == estudiante) {
            tipoUsuario = "Estudiante";
        } else if (e.getSource() == conductor) {
            tipoUsuario = "Conductor";
        } else if (e.getSource() == registroButton) {
            registrarUsuario();
        }
    }
}
