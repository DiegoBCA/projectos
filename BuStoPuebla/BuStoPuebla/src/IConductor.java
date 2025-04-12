import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class IConductor extends JPanel {
    private JTextArea statusTextArea;
    private JTextArea reportTextArea;

    public IConductor() {
        setLayout(new BorderLayout());

        JLabel statusLabel = new JLabel("Estado del Conductor:");
        statusTextArea = new JTextArea();
        statusTextArea.setEditable(false);
        statusTextArea.setLineWrap(true);
        statusTextArea.setWrapStyleWord(true);

        JButton statusButton = new JButton("Ver Estado");
        JButton accidentButton = new JButton("Reportar Accidente");
        reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);
        reportTextArea.setLineWrap(true);
        reportTextArea.setWrapStyleWord(true);

        JPanel statusPanel = new JPanel(new BorderLayout());
        JPanel reportPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());

        statusPanel.add(statusLabel, BorderLayout.NORTH);
        statusPanel.add(new JScrollPane(statusTextArea), BorderLayout.CENTER);

        reportPanel.add(new JScrollPane(reportTextArea), BorderLayout.CENTER);

        buttonPanel.add(statusButton);
        buttonPanel.add(accidentButton);

        add(statusPanel, BorderLayout.CENTER);
        add(reportPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.NORTH);

        // Acción para el botón "Ver Estado"
        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusTextArea.setText("El conductor está operando normalmente.");
            }
        });

        // Acción para el botón "Reportar Accidente"
        accidentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String detalles = JOptionPane.showInputDialog("Ingrese detalles del accidente:");
                if (detalles != null && !detalles.trim().isEmpty()) {
                    reportTextArea.setText("Accidente reportado el " + LocalDateTime.now() +
                                           "\nDetalles: " + detalles);
                } else {
                    JOptionPane.showMessageDialog(null, "No se ingresaron detalles del accidente.");
                }
            }
        });
    }
}
