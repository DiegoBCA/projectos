import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelQuejasSugerencias extends JPanel {
    private JTextField usuarioField;
    private JTextArea quejaArea;
    private JButton enviarBtn;

    public PanelQuejasSugerencias() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Quejas y Sugerencias", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(4, 1, 10, 10));
        centro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        usuarioField = new JTextField();
        quejaArea = new JTextArea(5, 20);
        quejaArea.setLineWrap(true);
        quejaArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(quejaArea);

        centro.add(new JLabel("Nombre de usuario:"));
        centro.add(usuarioField);
        centro.add(new JLabel("Escribe tu queja o sugerencia:"));
        centro.add(scroll);

        add(centro, BorderLayout.CENTER);

        enviarBtn = new JButton("Enviar");
        enviarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usuarioField.getText().trim();
                String queja = quejaArea.getText().trim();
                if (!usuario.isEmpty() && !queja.isEmpty()) {
                    GestionQuejas.insertarQueja(usuario, queja);
                    JOptionPane.showMessageDialog(null, "¡Gracias por tu mensaje!");
                    usuarioField.setText("");
                    quejaArea.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.");
                }
            }
        });
        add(enviarBtn, BorderLayout.SOUTH);
    }
}
