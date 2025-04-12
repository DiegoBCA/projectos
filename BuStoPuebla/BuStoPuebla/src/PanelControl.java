

import java.awt.*;
import javax.swing.*;

public class PanelControl extends JPanel {
    public PanelControl(JTextField[] campos) {
        setLayout(new GridLayout(1, 5, 5, 5));

        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.addActionListener(new AgregarRegistroEnc(campos));
        add(botonAgregar);

        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(new BuscarRegistroEnc(campos));
        add(botonBuscar);

        JButton botonActualizar = new JButton("Actualizar");
        botonActualizar.addActionListener(new ActualizarRegistroEnc(campos));
        add(botonActualizar);

        JButton botonLimpiar = new JButton("Limpiar");
        botonLimpiar.addActionListener(new LimpiarCampos(campos));
        add(botonLimpiar);

        JButton botonAyuda = new JButton("Ayuda");
        botonAyuda.addActionListener(new Ayuda());
        add(botonAyuda);
    }
}
