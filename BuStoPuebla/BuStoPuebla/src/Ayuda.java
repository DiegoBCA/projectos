import java.awt.event.*;
import javax.swing.*;

public class Ayuda implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent evento) {
        String mensaje = "Instrucciones de Uso:\n\n" +
        "1. Para AGREGAR un registro:\n" +
        "   - Llene todos los campos del formulario.\n" +
        "   - Haga clic en el botón 'Agregar'.\n\n" +
        "2. Para BUSCAR un registro:\n" +
        "   - Ingrese el nombre y apellido.\n" +
        "   - Presione 'Buscar'.\n\n" +
        "3. Para ACTUALIZAR un registro:\n" +
        "   - Ingrese nombre y apellido para localizar al contacto.\n" +
        "   - Actualice los demás campos y presione 'Actualizar'.\n\n" +
        "4. Para LIMPIAR el formulario:\n" +
        "   - Presione el botón 'Limpiar'.\n\n" +
        "Todos los datos se almacenan en una base de datos SQLite llamada 'administrador.db'.";
        JOptionPane.showMessageDialog(null, mensaje, "Ayuda", JOptionPane.INFORMATION_MESSAGE);
    }
}