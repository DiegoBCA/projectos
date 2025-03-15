import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class proyectoTestJlist {

    public static void main(String[] args) {
        // Crear la ventana principal
        JFrame frame = new JFrame("Ejemplo de JList");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Crear una lista de datos para el JList
        String[] rutas = {"Ruta 1", "Ruta 2", "Ruta 3", "Ruta 4", "Ruta 5"};

        // Crear el JList y pasarle el array de datos
        JList<String> list = new JList<>(rutas);
        
        // Hacer que el JList tenga un tamaño fijo para mostrar los elementos
        list.setVisibleRowCount(5);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Colocar el JList dentro de un JScrollPane (para que sea desplazable si es necesario)
        JScrollPane scrollPane = new JScrollPane(list);

        // Crear un botón para mostrar el elemento seleccionado
        JButton button = new JButton("Mostrar Selección");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = list.getSelectedValue();
                if (selectedItem == rutas[0]) {
                    JOptionPane.showMessageDialog(frame, "Chofer: Juan Perez\nHorario: 9:00 - 19:00\nStatus: Activo");
                }
                if (selectedItem == rutas[1]){
                    JOptionPane.showMessageDialog(frame, "Chofer: Armando Paredes\nHorario: 7:00 - 15:00\nStatus: Activo");
                }
                if (selectedItem == rutas[2]){
                    JOptionPane.showMessageDialog(frame, "Chofer:Juan Hernandez\nHorario: 10:00 - 13:00\nStatus: Activo");
                }
                if (selectedItem == rutas[3]){
                    JOptionPane.showMessageDialog(frame, "Chofer: Alonso Cobos\nHorario: 9:00 - 20:00\nStatus: Activo");
                }
                if (selectedItem == rutas[4]){
                    JOptionPane.showMessageDialog(frame, "Chofer: Oscar Perez\nHorario: 11:00 - 13:00\nStatus: Activo");
                }
            }
        });

        // Agregar los componentes a la ventana
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);

        // Hacer visible la ventana
        frame.setVisible(true);
    }
}
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
