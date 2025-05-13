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

        // Menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menuPrincipal = new JMenu("Opciones");

        verRuta = new JMenuItem("Ver Ruta");
        queja = new JMenuItem("Reportar Queja");
        salir = new JMenuItem("Salir");
        usuario = new JMenuItem("Opciones de Usuario");

        rutasFavMenu = new JMenu("Rutas Favoritas");

        menuPrincipal.add(verRuta);
        menuPrincipal.add(queja);
        menuPrincipal.add(salir);
        menuPrincipal.add(usuario);

        verRuta.addActionListener(this);
        queja.addActionListener(this);
        salir.addActionListener(this);
        usuario.addActionListener(this);

        menuBar.add(menuPrincipal);
        menuBar.add(rutasFavMenu);
        setJMenuBar(menuBar);

        // Panel principal de rutas
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
            JOptionPane.showMessageDialog(null, "Error al cargar rutas: " + e.getMessage());
        }
    }

    @Override
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

    public void quejar() {
        System.out.println("Abriendo ventana de quejas...");
        JFrame frameQuejas = new JFrame("Enviar Queja o Sugerencia");
        frameQuejas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameQuejas.setSize(500, 350);
        frameQuejas.setLocationRelativeTo(this);

        PanelQuejasSugerencias panel = new PanelQuejasSugerencias();
        frameQuejas.setContentPane(panel);

        frameQuejas.setVisible(true);
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
