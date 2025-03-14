import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Cliente extends JFrame implements ActionListener{
    private JMenu rutasFavMenu;
    private JMenuItem verRuta, queja, salir, rutaFavLabel;
    private JPanel rutasPanel;
    private JLabel rutaLabel;
    private String[] rutas = {"Ruta1", "Ruta2", "Ruta3", "Ruta4"};
    private ArrayList<String> rutasFav = new ArrayList<>();

    public Cliente(){
        super("Cliente");
        setLayout(new FlowLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menuPrincipal = new JMenu("Opciones");
        verRuta = new JMenuItem("Ver Ruta");
        queja = new JMenuItem("Reportar Queja");
        salir = new JMenuItem("Salir");

        rutasFavMenu = new JMenu("Rutas Favoritas");

        menuPrincipal.add(verRuta);
        verRuta.addActionListener(this);
        menuPrincipal.add(queja);
        queja.addActionListener(this);
        menuPrincipal.add(salir);
        salir.addActionListener(this);

        menuBar.add(menuPrincipal);
        menuBar.add(rutasFavMenu);
        setJMenuBar(menuBar);

        rutasPanel = new JPanel();
        rutasPanel.setLayout(new GridLayout(0, 2, 10, 10));
        add(rutasPanel, BorderLayout.CENTER);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    public void actionPerformed(ActionEvent event){
        if(event.getSource() == verRuta){
            mostrarRuta();
        }else if(event.getSource() == queja){
            quejar();
        }else if(event.getSource() == salir){
            volver();
        }
    }
    public void mostrarRuta(){
        rutasPanel.removeAll();
        //for i in rutas:
        for (String i : rutas){
            rutaLabel = new JLabel(i);
            JToggleButton toggleButton = new JToggleButton("Guardar");

            toggleButton.addItemListener(e -> {
                if (toggleButton.isSelected()) {
                    agregarFav(i);
                    toggleButton.setText("Guardado");
                } else {
                    eliminarFav(i);
                    toggleButton.setText("Guardar");
                }
            });
            rutasPanel.add(rutaLabel);
            rutasPanel.add(toggleButton);
        }
        rutasPanel.revalidate();
        rutasPanel.repaint();
    }
    public void volver(){
        //Sería volver al login
    }
    public void quejar(){
        System.out.println("Reportando queja...");
    }
    public void menuFavActualizar(){
        rutasFavMenu.removeAll();
        for(String i: rutasFav){
            rutaFavLabel = new JMenuItem (i);
            rutasFavMenu.add(rutaFavLabel);
        }
    }
    public void agregarFav(String ruta){
        if(!rutasFav.contains(ruta)){
            rutasFav.add(ruta);
        }
        menuFavActualizar();
    }
    public void eliminarFav(String ruta){
        rutasFav.remove(ruta);
        menuFavActualizar();
    }
}