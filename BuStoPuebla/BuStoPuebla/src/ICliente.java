import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Cliente extends JFrame implements ActionListener{
    private JMenu rutasFavMenu;
    private JMenuItem verRuta, infoRuta, queja, salir, rutaFavLabel, usuario;
    private JPanel rutasPanel;
    private JLabel rutaLabel;
    private JScrollPane scrollPane;
    private String[] rutas = {"Ruta1", "Ruta2", "Ruta3", "Ruta4", "Ruta5"};
    private ArrayList<String> rutasFav = new ArrayList<>();

    public Cliente(){
        super("Cliente");
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menuPrincipal = new JMenu("Opciones");
        verRuta = new JMenuItem("Ver Ruta");
        infoRuta = new JMenuItem("Más Información");
        queja = new JMenuItem("Reportar Queja");
        salir = new JMenuItem("Salir");
        usuario = new JMenuItem("Opciones de Usuario");

        rutasFavMenu = new JMenu("Rutas Favoritas");

        menuPrincipal.add(verRuta);
        verRuta.addActionListener(this);
        menuPrincipal.add(infoRuta);
        infoRuta.addActionListener(this);
        menuPrincipal.add(queja);
        queja.addActionListener(this);
        menuPrincipal.add(salir);
        salir.addActionListener(this);
        menuPrincipal.add(usuario);
        usuario.addActionListener(this);

        menuBar.add(menuPrincipal);
        menuBar.add(rutasFavMenu);
        setJMenuBar(menuBar);

        rutasPanel = new JPanel();
        rutasPanel.setLayout(new GridLayout(0, 2, 10, 10));
        add(rutasPanel, BorderLayout.CENTER);

        scrollPane = new JScrollPane(rutasPanel);
        add(scrollPane, BorderLayout.CENTER);

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
        }else if(event.getSource() == infoRuta){
            new RutasInfo();
        }else if(event.getSource() == usuario){
            new IUsuario();
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
        System.exit(0);
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
