import javax.swing.*;
import java.awt.*;

public class RutasInfo extends JFrame {
    public RutasInfo() {
        super("Información de Rutas");
        setLayout(new BorderLayout());

        // Lista de rutas
        String[] rutas = {"Ruta 1", "Ruta 2", "Ruta 3", "Ruta 4", "Ruta 5"};
        JList<String> list = new JList<>(rutas);
        list.setVisibleRowCount(5);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // JScrollPane para la lista
        JScrollPane scrollPane = new JScrollPane(list);

        // Botón para mostrar información de la ruta
        JButton button = new JButton("Mostrar Selección");
        button.addActionListener(e -> {
            String selectedItem = list.getSelectedValue();
            if (selectedItem != null) {
                String info = obtenerInfoRuta(selectedItem);
                JOptionPane.showMessageDialog(this, info);
            }
        });

        // Agregar los componentes a la ventana
        add(scrollPane, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);

        // Configuración de la ventana
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String obtenerInfoRuta(String ruta) {
        switch (ruta) {
            case "Ruta 1": return "Chofer: Juan Perez\nHorario: 9:00 - 19:00\nStatus: Activo";
            case "Ruta 2": return "Chofer: Armando Paredes\nHorario: 7:00 - 15:00\nStatus: Activo";
            case "Ruta 3": return "Chofer: Juan Hernandez\nHorario: 10:00 - 13:00\nStatus: Activo";
            case "Ruta 4": return "Chofer: Alonso Cobos\nHorario: 9:00 - 20:00\nStatus: Activo";
            case "Ruta 5": return "Chofer: Oscar Perez\nHorario: 11:00 - 13:00\nStatus: Activo";
            default: return "No hay información disponible.";
        }
    }
}
