import javax.swing.*;
public class IConductor extends JFrame{
    public IConductor(){
        setTitle("Interfaz Conductor");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel label = new JLabel("Bienvenido conductor", SwingConstants.CENTER);
        add(label);
        
        setVisible(true);
    }
    
}
