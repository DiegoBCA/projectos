import java.awt.*;
import javax.swing.*;

public class PanelDesplazable extends JPanel {
    private final JTextField[] campos;
    //firstname, lastname, busnumber, license, route, plate, schedule, toll, salary, complaints
    private final String[] etiquetas = {
        "Nombre:", "Apellido:", "ID de autobus:", "Licencia:", "Ruta:",
        "Placa:", "Horario:", "Peaje:", "Salario:", "Quejas:"
    };

    public PanelDesplazable() {
        setLayout(new GridLayout(etiquetas.length, 2, 5, 5));
        campos = new JTextField[etiquetas.length];

        for (int i = 0; i < etiquetas.length; i++) {
            add(new JLabel(etiquetas[i]));
            campos[i] = new JTextField(25);
            add(campos[i]);
        }
    }

    public JTextField[] obtenerCampos() {
        return campos;
    }
}