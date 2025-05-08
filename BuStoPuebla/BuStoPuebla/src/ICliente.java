import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class ICliente extends JFrame implements ActionListener {
    private JMenu rutasFavMenu;
    private JMenuItem verRuta, queja, salir, rutaFavLabel, usuario;
    private JPanel rutasPanel;
    private JLabel rutaLabel;
    private JScrollPane scrollPane;
    private ArrayList<String> rutas = new ArrayList<>();
    private ArrayList<String> rutasFav = new ArrayList<>();

    public ICliente() {
        super("Cliente");
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menuPrincipal = new JMenu("Opciones");
        verRuta = new JMenuItem("Ver Ruta");
        queja = new JMenuItem("Reportar Queja");
        salir = new JMenuItem("Salir");
        usuario = new JMenuItem("Opciones de Usuario");

        rutasFavMenu = new JMenu("Rutas Favoritas");

        menuPrincipal.add(verRuta);
        verRuta.addActionListener(this);
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
        scrollPane = new JScrollPane(rutasPanel);
        add(scrollPane, BorderLayout.CENTER);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        cargarRutasDesdeAddresses();
    }

    private void cargarRutasDesdeAddresses() {
        rutas.clear();
        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db")) {
            String sql = "SELECT DISTINCT route FROM addresses WHERE route IS NOT NULL AND route != ''";
            try (PreparedStatement stmt = conexion.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rutas.add(rs.getString("route"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al cargar rutas: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == verRuta) {
            mostrarRuta();
        } else if (event.getSource() == queja) {
            quejar();
        } else if (event.getSource() == salir) {
            volver();
        } else if (event.getSource() == usuario) {
            new IUsuario();
        }
    }

    public void mostrarRuta() {
        rutasPanel.removeAll();
        for (String ruta : rutas) {
            rutaLabel = new JLabel(ruta);
            boolean esFavorita = rutasFav.contains(ruta);
            JToggleButton toggleButton = new JToggleButton(esFavorita ? "Guardado" : "Guardar", esFavorita);
    
            toggleButton.addItemListener(e -> {
                if (toggleButton.isSelected()) {
                    agregarFav(ruta);
                    toggleButton.setText("Guardado");
                } else {
                    eliminarFav(ruta);
                    toggleButton.setText("Guardar");
                }
            });
    
            rutasPanel.add(rutaLabel);
            rutasPanel.add(toggleButton);
        }
        rutasPanel.revalidate();
        rutasPanel.repaint();
    }
    

    public void volver() {
        JFrame ventana = new JFrame("Inicio de Sesión");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(400, 250);
        ventana.setLocationRelativeTo(null);
        InicioSesion panelInicio = new InicioSesion(ventana);
        ventana.setContentPane(panelInicio);
        ventana.setVisible(true);
        this.dispose();
    }

    public void quejar() {
        System.out.println("Reportando queja...");
    }

    public void menuFavActualizar() {
        rutasFavMenu.removeAll();
        for (String ruta : rutasFav) {
            rutaFavLabel = new JMenuItem(ruta);
            rutasFavMenu.add(rutaFavLabel);
        }
    }

    public void agregarFav(String ruta) {
        if (!rutasFav.contains(ruta)) {
            rutasFav.add(ruta);
        }
        menuFavActualizar();
    }

    public void eliminarFav(String ruta) {
        rutasFav.remove(ruta);
        menuFavActualizar();
    }
}
