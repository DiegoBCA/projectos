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
