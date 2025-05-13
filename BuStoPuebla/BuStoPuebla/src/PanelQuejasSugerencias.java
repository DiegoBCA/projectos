import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PanelQuejasSugerencias extends JPanel {
    private JTextField usuarioField;
    private JTextArea quejaArea;
    private JButton enviarBtn;
    private JButton regresarBtn;
    private JComboBox<String> rutaCombo;

    public PanelQuejasSugerencias() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Quejas y Sugerencias", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Parte superior con campos
        JPanel campos = new JPanel(new GridLayout(4, 1, 10, 10));
        usuarioField = new JTextField();
        rutaCombo = new JComboBox<>();
        cargarRutasDesdeBD();

        campos.add(new JLabel("Nombre de usuario:"));
        campos.add(usuarioField);
        campos.add(new JLabel("Ruta relacionada:"));
        campos.add(rutaCombo);

        centro.add(campos, BorderLayout.NORTH);

        // Campo de queja más grande
        quejaArea = new JTextArea(10, 30);  // ⬅️ más grande que antes
        quejaArea.setLineWrap(true);
        quejaArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(quejaArea);

        JPanel campoQueja = new JPanel(new BorderLayout());
        campoQueja.add(new JLabel("Escribe tu queja o sugerencia:"), BorderLayout.NORTH);
        campoQueja.add(scroll, BorderLayout.CENTER);

        centro.add(campoQueja, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);

        // Botones
        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        enviarBtn = new JButton("Enviar");
        regresarBtn = new JButton("Regresar");

        enviarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usuarioField.getText().trim();
                String queja = quejaArea.getText().trim();
                String rutaSeleccionada = (String) rutaCombo.getSelectedItem();

                if (!usuario.isEmpty() && !queja.isEmpty() && rutaSeleccionada != null) {
                    String mensaje = "Ruta: " + rutaSeleccionada + " | Queja: " + queja;
                    GestionQuejas.insertarQueja(usuario, mensaje);
                    JOptionPane.showMessageDialog(null, "¡Gracias por tu mensaje!");
                    usuarioField.setText("");
                    quejaArea.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.");
                }
            }
        });

        regresarBtn.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(PanelQuejasSugerencias.this);
            if (ventana != null) ventana.dispose();
        });

        botonPanel.add(enviarBtn);
        botonPanel.add(regresarBtn);
        add(botonPanel, BorderLayout.SOUTH);
    }

    private void cargarRutasDesdeBD() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:administrador.db")) {
            String sql = "SELECT DISTINCT route FROM addresses WHERE route IS NOT NULL AND route != ''";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rutaCombo.addItem(rs.getString("route"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar rutas: " + e.getMessage());
        }
    }
}
