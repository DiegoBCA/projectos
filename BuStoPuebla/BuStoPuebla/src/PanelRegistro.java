import java.awt.*;
import javax.swing.*;

public class PanelRegistro extends JPanel {
    private final JTextField[] campos;
    private final String[] etiquetas = {
        "Nombre:", "Apellido:", "Dirección:", "Teléfono Personal:", "Correo Electronico:", "Ocupación:",  "Contraseña:"
    };

    public PanelRegistro() {
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
