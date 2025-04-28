import javax.swing.*;
import java.awt.*;

class InicioSesion extends JPanel {
    private final JTextField correoField;
    private final JPasswordField contraseñaField;

    public InicioSesion(JFrame framePrincipal) {
        //Damos forma para que ingresen los datos de inicio de sesión
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Correo Electrónico:"),gbc);
        gbc.gridx = 1;
        correoField = new JTextField(25);
        add(correoField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Contraseña:"),gbc);
        gbc.gridx = 1;
        contraseñaField = new JPasswordField(25);
        add(contraseñaField,gbc);
        //Buscamos en la base de datos si el usuario existe y es correcto
        gbc.gridx = 0;
        gbc.gridy = 2;
        JButton botonBuscar = new JButton("Iniciar Sesión");
        JTextField[] campos = { null, null, null, correoField, contraseñaField };
        // Se pasa el array de campos y el frame principal al constructor de BuscarRegistro
        botonBuscar.addActionListener(new BuscarRegistro(campos,framePrincipal));
        add(botonBuscar,gbc);
        // Si el usuario no existe, se le da la opción de registrarse
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton botonRegistrar = new JButton("Registrarse");
        botonRegistrar.addActionListener(e -> {
            framePrincipal.dispose(); 
            //Se abre la ventana de registro 
            JFrame frameRegistro = new JFrame("Registro de Usuario");
            frameRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameRegistro.setSize(300, 350);
            frameRegistro.setLocationRelativeTo(null);
            frameRegistro.setContentPane(new RegistroUsuario(frameRegistro)); 
            frameRegistro.setVisible(true);
        });
        add(botonRegistrar,gbc);
        
    }
}

