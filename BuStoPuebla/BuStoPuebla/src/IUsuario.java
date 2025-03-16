import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Usuario {
    private String nombre;
    private int id;
    private String correo;

    public Usuario(String nombre, int id, String correo) {
        this.nombre = nombre;
        this.id = id;
        this.correo = correo;
    }

    public void consultarUbicacion() {
        JOptionPane.showMessageDialog(null, "Consultando ubicación actual...");
    }

    public void consultarParadas() {
        JOptionPane.showMessageDialog(null, "Mostrando paradas cercanas...");
    }

    public void consultarCosto() {
        JOptionPane.showMessageDialog(null, "El costo del viaje es: $10.00");
    }

    public void reservarAsiento() {
        JOptionPane.showMessageDialog(null, "Asiento reservado exitosamente.");
    }

    public void guardarTransporte() {
        JOptionPane.showMessageDialog(null, "Transporte guardado en favoritos.");
    }
}

public class IUsuario extends JFrame implements ActionListener {
    JButton ubicacionButton, paradasButton, costoButton, reservaButton, guardarButton;
    Usuario usuario;

    public IUsuario() {
        super("Interfaz de Usuario");
        setLayout(new FlowLayout());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        usuario = new Usuario("Juan Pérez", 101, "juan.perez@example.com");

        ubicacionButton = new JButton("Consultar Ubicación");
        paradasButton = new JButton("Consultar Paradas");
        costoButton = new JButton("Consultar Costo");
        reservaButton = new JButton("Reservar Asiento");
        guardarButton = new JButton("Guardar Transporte");

        add(ubicacionButton);
        add(paradasButton);
        add(costoButton);
        add(reservaButton);
        add(guardarButton);

        ubicacionButton.addActionListener(this);
        paradasButton.addActionListener(this);
        costoButton.addActionListener(this);
        reservaButton.addActionListener(this);
        guardarButton.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == ubicacionButton) {
            usuario.consultarUbicacion();
        } else if (event.getSource() == paradasButton) {
            usuario.consultarParadas();
        } else if (event.getSource() == costoButton) {
            usuario.consultarCosto();
        } else if (event.getSource() == reservaButton) {
            usuario.reservarAsiento();
        } else if (event.getSource() == guardarButton) {
            usuario.guardarTransporte();
        }
    }
}
