package org.example;
import org.example.pelicula.controlador.ControladorP;
import org.example.pelicula.model.AsigPelicula;
import org.example.pelicula.view.PanelPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AsigPelicula model = new AsigPelicula();

            PanelPrincipal view = new PanelPrincipal();

            ControladorP controller = new ControladorP(view, model);
        });
    }
}