import javax.swing.*;

public class RegistroUsuario {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("📘 Agenda de Direcciones (SQLite)");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(600, 320);

        PanelRegistro panelDatos = new PanelRegistro();
        JScrollPane scrollPane = new JScrollPane(panelDatos);
        ventana.add(scrollPane, "Center");

        PanelBotones panelBotones = new PanelBotones(panelDatos.obtenerCampos());
        ventana.add(panelBotones, "South");

        ventana.setVisible(true);
    }
}
