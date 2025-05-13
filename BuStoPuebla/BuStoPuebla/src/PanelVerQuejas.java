import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelVerQuejas extends JPanel {
    private JTextArea areaQuejas;
    private JButton actualizarBtn;
    private JButton regresarBtn;

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

        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actualizarBtn = new JButton("Actualizar Lista");
        regresarBtn = new JButton("Regresar");

        actualizarBtn.addActionListener(e -> cargarQuejas());

        regresarBtn.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(PanelVerQuejas.this);
            if (ventana != null) ventana.dispose();
        });

        botonPanel.add(actualizarBtn);
        botonPanel.add(regresarBtn);
        add(botonPanel, BorderLayout.SOUTH);

        cargarQuejas(); 
    }

    private void cargarQuejas() {
        List<String> quejas = GestionQuejas.obtenerQuejas();
        areaQuejas.setText("");

        for (String q : quejas) {
            String[] partes = q.split("\\|");
            if (partes.length == 2) {
                areaQuejas.append("Ruta: " + partes[0].replace("Ruta:", "").trim() + "\n");
                areaQuejas.append("Queja: " + partes[1].replace("Queja:", "").trim() + "\n\n");
            } else {
                areaQuejas.append("• " + q + "\n\n"); 
            }
        }
    }
}
