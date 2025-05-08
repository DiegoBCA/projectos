import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class IUsuario extends JFrame implements ActionListener {
    JButton ubicacionButton, paradasButton, costoButton, reservaButton;
    String rutaSeleccionada;

    public IUsuario() {
        super("Interfaz de Usuario");
        setLayout(new FlowLayout());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        rutaSeleccionada = seleccionarRuta();

        ubicacionButton = new JButton("Consultar Información");
        paradasButton = new JButton("Consultar Paradas");
        costoButton = new JButton("Consultar Costo");
        reservaButton = new JButton("Reservar Asiento");

        add(ubicacionButton);
        add(paradasButton);
        add(costoButton);
        add(reservaButton);

        ubicacionButton.addActionListener(this);
        paradasButton.addActionListener(this);
        costoButton.addActionListener(this);
        reservaButton.addActionListener(this);

        setVisible(true);
    }

    private String seleccionarRuta() {
        ArrayList<String> rutas = new ArrayList<>();
        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db")) {
            String sql = "SELECT DISTINCT route FROM addresses WHERE route IS NOT NULL AND route != ''";
            try (PreparedStatement stmt = conexion.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rutas.add(rs.getString("route"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al obtener rutas: " + e.getMessage());
        }

        if (rutas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ No hay rutas disponibles en la base de datos.");
            return null;
        }

        Object seleccion = JOptionPane.showInputDialog(null, "Seleccione una ruta:", "Rutas Disponibles",
                JOptionPane.QUESTION_MESSAGE, null, rutas.toArray(), rutas.get(0));
        return seleccion != null ? seleccion.toString() : null;
    }

    public void consultarUbicacion() {
        if (rutaSeleccionada == null) return;
        String conductor = "", horario = "", peaje = "";
        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db")) {
            String sql = "SELECT firstname, schedule, toll FROM addresses WHERE route = ? LIMIT 1";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setString(1, rutaSeleccionada);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    conductor = rs.getString("firstname");
                    horario = rs.getString("schedule");
                    peaje = rs.getString("toll");
                } else {
                    JOptionPane.showMessageDialog(null, "⚠️ No se encontró información para esta ruta.");
                    return;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al consultar la base de datos: " + e.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(null,
                "📍 Ruta: " + rutaSeleccionada +
                "\n👤 Conductor: " + conductor +
                "\n🕒 Horario: " + horario +
                "\n💲 Peaje: " + peaje);
    }

    public void consultarParadas() {
        if (rutaSeleccionada != null) {
            JOptionPane.showMessageDialog(null, "📍 Mostrando paradas cercanas para la ruta: " + rutaSeleccionada);
        }
    }

    public void consultarCosto() {
        if (rutaSeleccionada == null) return;
        String peaje = "";
        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:administrador.db")) {
            String sql = "SELECT toll FROM addresses WHERE route = ? LIMIT 1";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setString(1, rutaSeleccionada);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    peaje = rs.getString("toll");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al consultar costo: " + e.getMessage());
        }

        JOptionPane.showMessageDialog(null, "💰 El costo de la ruta " + rutaSeleccionada + " es: $" + peaje);
    }

    public void reservarAsiento() {
        if (rutaSeleccionada != null) {
            JOptionPane.showMessageDialog(null, "✅ Asiento reservado exitosamente para la ruta: " + rutaSeleccionada);
        }
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == ubicacionButton) {
            consultarUbicacion();
        } else if (event.getSource() == paradasButton) {
            consultarParadas();
        } else if (event.getSource() == costoButton) {
            consultarCosto();
        } else if (event.getSource() == reservaButton) {
            reservarAsiento();
        }
    }
}
