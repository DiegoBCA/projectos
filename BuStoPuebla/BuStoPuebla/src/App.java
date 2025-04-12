import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        
            JFrame ventana = new JFrame("Inicio de Sesión");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(400, 250);
            ventana.setLocationRelativeTo(null);
            InicioSesion panelInicio = new InicioSesion(ventana);
            ventana.setContentPane(panelInicio);
            ventana.setVisible(true);
    }
}
