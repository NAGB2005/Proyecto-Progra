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

import javax.swing.JOptionPane;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

/**
 * <p>Controlador principal de la aplicación de cine.</p>
 * <p>Esta clase gestiona la navegación entre las diferentes vistas (PanelPrincipal, PanelDetallePelicula, PanelAsientos) y
 * maneja la lógica de negocio, como la selección de películas, horarios y asientos. Actúa como el puente entre el modelo de datos y las vistas.</p>
 */
public class ControladorP implements InterfazPanelAsientos, InterfazPanelPrincipal {

    private PanelPrincipal panelPrincipal;
    private PanelDetallePelicula panelDetallePelicula;
    private PanelAsientos panelAsientos;

    private AsigPelicula asigPeliculaModel;
    private Sala salaActual;
    private Pelicula peliculaSeleccionada;

    /**
     * Constructor del controlador.
     * @param panelPrincipal La vista principal de la cartelera de películas.
     * @param asigPeliculaModel El modelo que contiene y gestiona los datos de las películas.
     */
    public ControladorP(PanelPrincipal panelPrincipal, AsigPelicula asigPeliculaModel) {
        this.panelPrincipal = panelPrincipal;
        this.asigPeliculaModel = asigPeliculaModel;

        this.panelPrincipal.setPeliculaActionListener(this);
        initialize();
    }

    /**
     * Inicializa la aplicación cargando las películas y mostrando el panel principal.
     */
    private void initialize() {
        List<Pelicula> peliculas = asigPeliculaModel.getPeliculas();
        panelPrincipal.displayPeliculas(peliculas);
        panelPrincipal.setVisible(true);
    }

    /**
     * Maneja el evento de selección de una película desde el panel principal.
     * Cierra la ventana principal y abre la ventana de detalles de la película seleccionada.
     * @param pelicula La película seleccionada por el usuario.
     */
    @Override
    public void onPeliculaSelected(Pelicula pelicula) {
        this.peliculaSeleccionada = pelicula;

        if (panelPrincipal != null) {
            panelPrincipal.dispose();
        }

        if (panelDetallePelicula != null) {
            panelDetallePelicula.dispose();
        }
        panelDetallePelicula = new PanelDetallePelicula(pelicula, this);
        panelDetallePelicula.setVisible(true);
    }

    /**
     * Maneja el evento de selección de un horario desde el panel de detalles de la película.
     * Cierra la ventana de detalles y abre la ventana de selección de asientos para la función elegida.
     * @param pelicula La película seleccionada.
     * @param hora La hora de la función seleccionada.
     */
    @Override
    public void onScheduleSelected(Pelicula pelicula, String hora) {
        if (panelDetallePelicula != null) {
            panelDetallePelicula.dispose();
        }

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

    /**
     * Maneja el evento de selección/deselección de un asiento.
     * Actualiza el estado del asiento en la sala actual y refresca la vista del panel de asientos.
     * @param seatName El nombre del asiento (ej. "A1").
     */
    @Override
    public void onSeatToggle(String seatName) {
        if (salaActual != null) {
            salaActual.toggleAsientoSelection(seatName);
            panelAsientos.updateSeatsDisplay(salaActual.getAsientos());
            panelAsientos.updateSummaryDisplay(salaActual.getSelectedAsientos(), salaActual.getTotalPrice());
        }
    }

    /**
     * Maneja la compra de tickets.
     * Confirma la compra, actualiza los asientos ocupados en el modelo, guarda los cambios en el archivo,
     * cierra el panel de asientos y vuelve a la pantalla principal.
     */
    @Override
    public void onBuyTickets() {
        if (salaActual != null && !salaActual.getSelectedAsientos().isEmpty()) {
            String seats = "";
            for (Asiento asiento : salaActual.getSelectedAsientos()) {
                seats += asiento.getNombre() + " ";
            }

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

                // Cierra la ventana actual de asientos.
                panelAsientos.dispose();
                this.salaActual = null;

                // Vuelve a abrir la ventana principal con las películas actualizadas.
                // CORRECCIÓN: Se usa el nuevo constructor de PanelPrincipal que acepta una lista de películas.
                panelPrincipal = new PanelPrincipal(asigPeliculaModel.getPeliculas());
                panelPrincipal.setPeliculaActionListener(this);
                panelPrincipal.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(panelAsientos, "Por favor, selecciona al menos un asiento.", "Error de Selección", JOptionPane.WARNING_MESSAGE);
        }
    }
}