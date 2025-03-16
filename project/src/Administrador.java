import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Administrador extends JFrame implements ActionListener{
    JButton buscarButton, registrarButton, consultarButton;
    public Administrador(){
        super("Administrador");
        setLayout(new FlowLayout());

        buscarButton = new JButton("Buscar Unidades");
        registrarButton  = new JButton("Registrar Unidad");
        consultarButton = new JButton("Visualizar reporte");
        add(buscarButton);
        add(registrarButton);
        add(consultarButton);
        buscarButton.addActionListener(this);
        registrarButton.addActionListener(this);
        consultarButton.addActionListener(this);
    }
    public void actionPerformed(ActionEvent event){
        if (event.getSource() == buscarButton){
            buscar();
        }else if (event.getSource() == registrarButton){
            registrar();
        }else if (event.getSource() == consultarButton){
            consultar();
        }
    }
    public void buscar(){
        new RutasInfo();
    }
    public void registrar(){

    }
    public void consultar(){
        
    }
}
