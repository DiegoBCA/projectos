import javax.swing.*;

public class Usuario {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("📘 Agenda de Direcciones (SQLite)");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(600, 500);

        // Crear panel desplazable (campos de texto)
        PanelRegistro panelDatos = new PanelRegistro();
        JScrollPane scrollPane = new JScrollPane(panelDatos);
        ventana.add(scrollPane, "Center");

        // Crear panel de control (botones)
        PanelBotones panelBotones = new PanelBotones(panelDatos.obtenerCampos());
        ventana.add(panelBotones, "South");

        ventana.setVisible(true);
    }
}
