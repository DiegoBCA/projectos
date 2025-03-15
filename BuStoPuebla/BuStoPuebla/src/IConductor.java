import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class IConductor extends JFrame {
    private JTextArea statusTextArea;
    private JTextArea reportTextArea;

    public Conductor() {
        // Configuración del frame
        setTitle("Estado y Reporte del Conductor");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear componentes
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

        // Layout de componentes
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

        // Añadir ActionListener a los botones
        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusTextArea.setText("El conductor está operando normalmente.");
            }
        });

        accidentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportTextArea.setText("Accidente reportado el " + LocalDateTime.now() +
                                       "\nDetalles: [Descripción del accidente]");
            }
        });
    }
}
