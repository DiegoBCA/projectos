import javax.swing.*;
import java.awt.*;

class InicioSesion extends JPanel {
    private final JTextField correoField;
    private final JPasswordField contraseñaField;

    public InicioSesion(JFrame framePrincipal) {
        //Damos forma para que ingresen los datos de inicio de sesión
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Correo Electrónico:"));
        correoField = new JTextField(25);
        add(correoField);

        add(new JLabel("Contraseña:"));
        contraseñaField = new JPasswordField(25);
        add(contraseñaField);
        //Buscamos en la base de datos si el usuario existe y es correcto
        JButton botonBuscar = new JButton("Iniciar Sesión");
        JTextField[] campos = { null, null, null, correoField, contraseñaField };
        // Se pasa el array de campos y el frame principal al constructor de BuscarRegistro
        botonBuscar.addActionListener(new BuscarRegistro(campos,framePrincipal));
        add(botonBuscar);
        // Si el usuario no existe, se le da la opción de registrarse
        JButton botonRegistrar = new JButton("Registrarse");
        botonRegistrar.addActionListener(e -> {
            framePrincipal.dispose(); 
            //Se abre la ventana de registro 
            JFrame frameRegistro = new JFrame("Registro de Usuario");
            frameRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameRegistro.setSize(500, 500);
            frameRegistro.setLocationRelativeTo(null);
            frameRegistro.setContentPane(new IRegistroUsuario(frameRegistro)); 
            frameRegistro.setVisible(true);
        });
        add(botonRegistrar);
    }
}
