import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        Cliente frame2 = new Cliente();
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Administrador frame = new Administrador();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setVisible(true);
    }
}