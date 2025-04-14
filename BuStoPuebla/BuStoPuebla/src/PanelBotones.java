import java.awt.*;
import javax.swing.*;

public class PanelBotones extends JPanel {
    public PanelBotones(JTextField[] campos) {
        setLayout(new GridLayout(1, 5, 5, 5));

        JButton botonAgregar = new JButton("Agregar");
        //botonAgregar.addActionListener(new AgregarRegistro(campos));
        add(botonAgregar);

        JButton botonActualizar = new JButton("Actualizar");
        //botonActualizar.addActionListener(new ActualizarRegistro(campos));
        add(botonActualizar);

        JButton botonLimpiar = new JButton("Limpiar");
        //botonLimpiar.addActionListener(new LimpiarC ampos(campos));
        add(botonLimpiar);

        JButton botonAyuda = new JButton("Ayuda");
        botonAyuda.addActionListener(new botonAyuda());
        add(botonAyuda);
    }
}
