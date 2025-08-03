package org.example.pelicula.controlador;

import org.example.pelicula.model.AsigPelicula;
import org.example.pelicula.model.Pelicula;
import org.example.pelicula.model.Sala;
import org.example.pelicula.model.Asiento;
import org.example.pelicula.view.InterfazPanelAsientos;
import org.example.pelicula.view.InterfazPanelPrincipal;
import org.example.pelicula.view.PanelPrincipal;
import org.example.pelicula.view.PanelDetallePelicula;
import org.example.pelicula.view.PanelAsientos;
import org.example.pelicula.view.PeliculaActionListener;

import javax.swing.JOptionPane;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class ControladorP implements InterfazPanelAsientos, PeliculaActionListener, InterfazPanelPrincipal {

    private PanelPrincipal panelPrincipal;
    private PanelDetallePelicula panelDetallePelicula;
    private PanelAsientos panelAsientos;

    private AsigPelicula asigPeliculaModel;
    private Sala salaActual;
    private Pelicula peliculaSeleccionada;

    public ControladorP(PanelPrincipal panelPrincipal, AsigPelicula asigPeliculaModel) {
        this.panelPrincipal = panelPrincipal;
        this.asigPeliculaModel = asigPeliculaModel;

        this.panelPrincipal.setPeliculaActionListener(this);
        initialize();
    }

    private void initialize() {
        List<Pelicula> peliculas = asigPeliculaModel.getPeliculas();
        panelPrincipal.displayPeliculas(peliculas);
        panelPrincipal.setVisible(true);
    }

    @Override
    public void onPeliculaSelected(Pelicula pelicula) {
        this.peliculaSeleccionada = pelicula;
        if (panelDetallePelicula != null) {
            panelDetallePelicula.dispose();
        }
        panelDetallePelicula = new PanelDetallePelicula(pelicula, this);
        panelDetallePelicula.setVisible(true);
    }

    @Override
    public void onScheduleSelected(Pelicula pelicula, String hora) {
        if (panelAsientos != null) {
            panelAsientos.dispose();
        }

        Optional<Pelicula.Funcion> funcionOptional = pelicula.getFunciones().stream()
                .filter(f -> f.getHorario().equals(hora))
                .findFirst();

        if (funcionOptional.isPresent()) {
            Pelicula.Funcion funcion = funcionOptional.get();
            this.salaActual = new Sala(funcion.getPrecioTicket(), funcion.getAsientosOcupados());

            panelAsientos = new PanelAsientos(pelicula.getTitulo(), hora, salaActual.precioTicket);
            panelAsientos.setPeliculaActionListener(this);

            panelAsientos.updateSeatsDisplay(salaActual.getAsientos());
            panelAsientos.updateSummaryDisplay(salaActual.getSelectedAsientos(), salaActual.getTotalPrice());
            panelAsientos.setVisible(true);
        }
    }

    @Override
    public void onSeatToggle(String seatName) {
        if (salaActual != null) {
            salaActual.toggleAsientoSelection(seatName);
            panelAsientos.updateSeatsDisplay(salaActual.getAsientos());
            panelAsientos.updateSummaryDisplay(salaActual.getSelectedAsientos(), salaActual.getTotalPrice());
        }
    }

    @Override
    public void onBuyTickets() {
        if (salaActual != null && !salaActual.getSelectedAsientos().isEmpty()) {
            String seats = "";
            for (Asiento asiento : salaActual.getSelectedAsientos()) {
                seats += asiento.getNombre() + " ";
            }

            // Actualizar el modelo y el archivo de texto
            List<String> nuevosAsientosOcupados = new ArrayList<>();
            for (Asiento asiento : salaActual.getAsientos()) {
                if (asiento.isOcupado() || asiento.isSeleccionado()) {
                    nuevosAsientosOcupados.add(asiento.getNombre());
                }
            }

            Optional<Pelicula.Funcion> funcionOptional = peliculaSeleccionada.getFunciones().stream()
                    .filter(f -> f.getHorario().equals(panelAsientos.horaSeleccionada))
                    .findFirst();

            if (funcionOptional.isPresent()) {
                Pelicula.Funcion funcion = funcionOptional.get();
                funcion.setAsientosOcupados(nuevosAsientosOcupados);
                asigPeliculaModel.guardarPeliculas();

                JOptionPane.showMessageDialog(panelAsientos,
                        "¡Compra Exitosa!\nPelícula: " + panelAsientos.peliculaTitulo +
                                "\nHora: " + panelAsientos.horaSeleccionada +
                                "\nAsientos: " + seats.trim() +
                                "\nTotal: " + String.format("$%.2f", salaActual.getTotalPrice()),
                        "Confirmación de Compra", JOptionPane.INFORMATION_MESSAGE);
                panelAsientos.dispose();
                this.salaActual = null;
            }
        } else {
            JOptionPane.showMessageDialog(panelAsientos, "Por favor, selecciona al menos un asiento.", "Error de Selección", JOptionPane.WARNING_MESSAGE);
        }
    }
}