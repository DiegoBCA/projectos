import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelVerQuejas extends JPanel {
    private JTextArea areaQuejas;

    public PanelVerQuejas() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Lista de Quejas y Sugerencias", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        areaQuejas = new JTextArea();
        areaQuejas.setEditable(false);
        areaQuejas.setLineWrap(true);
        areaQuejas.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(areaQuejas);
        add(scrollPane, BorderLayout.CENTER);

        JButton actualizarBtn = new JButton("Actualizar Lista");
        actualizarBtn.addActionListener(e -> cargarQuejas());
        add(actualizarBtn, BorderLayout.SOUTH);

        cargarQuejas();
    }

    private void cargarQuejas() {
        List<String> quejas = GestionQuejas.obtenerQuejas();
        areaQuejas.setText("");
        for (String q : quejas) {
            areaQuejas.append(q + "\n\n");
        }
    }
}
