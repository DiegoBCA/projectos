import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class IUsuario extends JFrame implements ActionListener{
    JButton ubicacionButton, paradasButton, costoButton, reservaButton;

    public IUsuario(){
        super("Interfaz de Usuario");
        setLayout(new FlowLayout());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ubicacionButton = new JButton("Consultar Ubicación");
        paradasButton = new JButton("Consultar Paradas");
        costoButton = new JButton("Consultar Costo");
        reservaButton = new JButton("Reservar Asiento");

        add(ubicacionButton);
        add(paradasButton);
        add(costoButton);
        add(reservaButton);

        ubicacionButton.addActionListener(this);
        paradasButton.addActionListener(this);
        costoButton.addActionListener(this);
        reservaButton.addActionListener(this);

        setVisible(true);
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
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == ubicacionButton) {
            consultarUbicacion();
        } else if (event.getSource() == paradasButton) {
            consultarParadas();
        } else if (event.getSource() == costoButton) {
            consultarCosto();
        } else if (event.getSource() == reservaButton) {
            reservarAsiento();
        }
    }
}
