import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class InicioSesion extends JFrame implements ActionListener {
    private String tipoUsuario;
    private JTextField usuarioField;
    private JPasswordField passwordField;
    private JRadioButton administrador, estudiante, conductor;
    private JButton inicioButton, registroButton;

    public InicioSesion() {
        setTitle("Login Form");
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
        JLabel titulo = new JLabel("Inicio de sesión", SwingConstants.LEFT);
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
        add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1;
        usuarioField = new JTextField(12);
        add(usuarioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(12);
        add(passwordField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        inicioButton = new JButton("Iniciar Sesión");
        inicioButton.setBackground(Color.GREEN);
        add(inicioButton, gbc);
        inicioButton.addActionListener(this);

        gbc.gridy = 5;
        registroButton = new JButton("Registrarse");
        registroButton.setBackground(Color.BLUE);
        add(registroButton, gbc);
        registroButton.addActionListener(this);

        setVisible(true);
    }

    private void verificarCredenciales() {
        String usuario = usuarioField.getText();
        String password = new String(passwordField.getPassword());

        if (tipoUsuario == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un tipo de usuario", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

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
        JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
    }

    

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == administrador) {
            tipoUsuario = "Administrador";
        } else if (e.getSource() == estudiante) {
            tipoUsuario = "Estudiante";
        } else if (e.getSource() == conductor) {
            tipoUsuario = "Conductor";
        } else if (e.getSource() == inicioButton) {
            verificarCredenciales();
            if (tipoUsuario != null) { // Asegúrate de que el tipo de usuario no sea nulo
                if (tipoUsuario.equals("Administrador")) {
                    new Administrador(); // Abre la interfaz del administrador
                } else if (tipoUsuario.equals("Estudiante")) {
                    new Cliente(); // Abre la interfaz del estudiante
                } else if (tipoUsuario.equals("Conductor")) {
                    new Conductor();
                }
                this.dispose();
             }
        } else if(e.getSource() == registroButton){
            new RegistroUsuario();
        }
    }
}
