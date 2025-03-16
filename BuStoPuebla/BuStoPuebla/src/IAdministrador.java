import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IAdministrador extends JFrame implements ActionListener{
    JButton buscarButton;
    public IAdministrador(){
        super("Administrador");
        setLayout(new FlowLayout());

        buscarButton = new JButton("Buscar Unidades");
        add(buscarButton);
        buscarButton.addActionListener(this);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent event){
        if (event.getSource() == buscarButton){
            buscar();
        }
    }
    public void buscar(){
        new RutasInfo();
    }
}
