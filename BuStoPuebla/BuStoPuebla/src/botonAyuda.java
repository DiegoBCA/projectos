import java.awt.event.*;
import javax.swing.*;

public class botonAyuda implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent evento) {
        String mensaje = "Instrucciones de Uso:\n\n" +
        "1. Para AGREGAR un registro:\n" +
        "   - Llene todos los campos del formulario con su información personal.\n" +
        "   - Haga clic en el botón 'Agregar'.\n\n" +
        "2. Para ACTUALIZAR un registro:\n" +
        "   - Ingrese nombre y apellido para localizar sus datos.\n" +
        "   - Actualice los demás campos y presione 'Actualizar' para guardar los nuevos datos.\n\n" +
        "3. Para LIMPIAR el formulario:\n" +
        "   - Presione el botón 'Limpiar', limpiara automaticamente todos los apartados.\n\n" +
        "Todos los datos son confidenciales y estan encriptados para mayor seguridad.";
        JOptionPane.showMessageDialog(null, mensaje, "Ayuda", JOptionPane.INFORMATION_MESSAGE);
    }
}
