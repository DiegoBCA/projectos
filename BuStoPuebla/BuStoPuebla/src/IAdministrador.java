import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class IAdministrador extends JFrame implements ActionListener {

private JButton buscarButton;
private PanelDesplazable panelDatos;
private PanelControl panelBotones;

public IAdministrador() {
    super("Administrador");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 300);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    // Crear panel desplazable (campos de texto)
    panelDatos = new PanelDesplazable();
    JScrollPane scrollPane = new JScrollPane(panelDatos);
    add(scrollPane, BorderLayout.CENTER);

    // Crear panel de control (botones) y botón personalizado
    panelBotones = new PanelControl(panelDatos.obtenerCampos());

    // Añadir botón de búsqueda extra
    buscarButton = new JButton("Reporte");
    buscarButton.addActionListener(this);
    panelBotones.add(buscarButton);  
    add(panelBotones, BorderLayout.SOUTH);

    setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == buscarButton) {
            buscar();
        }
    }

    public void buscar() {
        new IUsuario();
    }
}
