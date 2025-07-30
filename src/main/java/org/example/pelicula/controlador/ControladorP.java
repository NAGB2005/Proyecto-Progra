package org.example.pelicula.controlador;
import org.example.pelicula.model.AsigPelicula;
import org.example.pelicula.model.Pelicula;
import org.example.pelicula.view.InterfazPanelAsientos;
import org.example.pelicula.view.InterfazPanelPrincipal;
import org.example.pelicula.model.Sala; // Importa la nueva clase Sala
import org.example.pelicula.model.Asiento; // Importa la nueva clase Asiento
import org.example.pelicula.view.PanelPrincipal;
import org.example.pelicula.view.PanelDetallePelicula;
import org.example.pelicula.view.PanelAsientos;
import org.example.pelicula.view.PeliculaActionListener; // Importa la interfaz del listener

import javax.swing.JOptionPane;
import java.util.List;

public class ControladorP implements InterfazPanelAsientos, PeliculaActionListener, InterfazPanelPrincipal {// Implementa la interfaz

    private PanelPrincipal panelPrincipal;
    private PanelDetallePelicula panelDetallePelicula; // Podría ser null si no está abierto
    private PanelAsientos panelAsientos; // Podría ser null

    private AsigPelicula asigPeliculaModel; // Instancia de tu modelo de películas
    private Sala salaActual; // El modelo de la sala de asientos para la sesión actual

    public ControladorP(PanelPrincipal panelPrincipal, AsigPelicula asigPeliculaModel) {
        this.panelPrincipal = panelPrincipal;
        this.asigPeliculaModel = asigPeliculaModel;

        // El controlador se registra como listener de la vista principal.
        this.panelPrincipal.setPeliculaActionListener(this);
        initialize();
    }

    private void initialize() {
        // Llama a AsigPelicula para obtener los datos.
        // Se llama a: AsigPelicula.getPeliculas()
        List<Pelicula> peliculas = asigPeliculaModel.getPeliculas();

        // Le dice a PanelPrincipal (la vista) que muestre esas películas.
        // Se llama a: PanelPrincipal.displayPeliculas(peliculas)
        panelPrincipal.displayPeliculas(peliculas);
        panelPrincipal.setVisible(true); // Asegúrate de hacer visible la ventana principal
    }

    // --- Implementación de PeliculaActionListener (Manejo de Eventos de la Vista) ---

    @Override
    public void onPeliculaSelected(Pelicula pelicula) {
        // Este método se llama cuando PanelPrincipal notifica que una película fue seleccionada.

        // Lógica del controlador: Cierra la ventana de detalle anterior si existe.
        if (panelDetallePelicula != null) {
            panelDetallePelicula.dispose();
        }
        // Crea una nueva instancia de PanelDetallePelicula (la vista de detalle).
        // Se llama a: new PanelDetallePelicula(pelicula)
        panelDetallePelicula = new PanelDetallePelicula(pelicula);
        // El controlador se registra como listener para esta nueva vista de detalle.
        // Se llama a: PanelDetallePelicula.setPeliculaActionListener(this)
        panelDetallePelicula.setPeliculaActionListener(this);
        // Hace visible la vista de detalle.
        panelDetallePelicula.setVisible(true);
    }

    @Override
    public void onScheduleSelected(Pelicula pelicula, String hora) {
        // Este método se llama cuando PanelDetallePelicula notifica que se seleccionó un horario.

        // Lógica del controlador: Cierra la ventana de asientos anterior si existe.
        if (panelAsientos != null) {
            panelAsientos.dispose();
        }
        // Crea una nueva instancia de Sala (el modelo de asientos) para esta sesión.
        // Aquí podrías cargar la configuración real de la sala desde una base de datos.
        // Se llama a: new Sala(precioTicket)
        this.salaActual = new Sala(7.50); // Precio del ticket fijo por ahora

        // Crea una nueva instancia de PanelAsientos (la vista de selección de asientos).
        // Se llama a: new PanelAsientos(pelicula.getTitulo(), hora, salaActual.precioTicket)
        panelAsientos = new PanelAsientos(pelicula.getTitulo(), hora, salaActual.precioTicket);
        // El controlador se registra como listener para esta nueva vista de asientos.
        // Se llama a: PanelAsientos.setPeliculaActionListener(this)
        panelAsientos.setPeliculaActionListener(this);

        // Le dice a PanelAsientos (la vista) que actualice la visualización de asientos con el modelo.
        // Se llama a: PanelAsientos.updateSeatsDisplay(salaActual.getAsientos())
        panelAsientos.updateSeatsDisplay(salaActual.getAsientos());
        // Le dice a PanelAsientos (la vista) que actualice el resumen de precios.
        // Se llama a: PanelAsientos.updateSummaryDisplay(salaActual.getSelectedAsientos(), salaActual.getTotalPrice())
        panelAsientos.updateSummaryDisplay(salaActual.getSelectedAsientos(), salaActual.getTotalPrice());
        // Hace visible la vista de asientos.
        panelAsientos.setVisible(true);
    }

    @Override
    public void onSeatToggle(String seatName) {
        // Este método se llama cuando PanelAsientos notifica que un asiento fue toggleado (seleccionado/deseleccionado).

        // Lógica del controlador: Actualiza el estado del asiento en el modelo Sala.
        // Se llama a: Sala.toggleAsientoSelection(seatName)
        if (salaActual != null) {
            salaActual.toggleAsientoSelection(seatName);
            // Luego, le dice a la Vista (PanelAsientos) que se actualice.
            // Se llama a: PanelAsientos.updateSeatsDisplay(salaActual.getAsientos())
            panelAsientos.updateSeatsDisplay(salaActual.getAsientos());
            // Se llama a: PanelAsientos.updateSummaryDisplay(salaActual.getSelectedAsientos(), salaActual.getTotalPrice())
            panelAsientos.updateSummaryDisplay(salaActual.getSelectedAsientos(), salaActual.getTotalPrice());
        }
    }

    @Override
    public void onBuyTickets() {
        // Este método se llama cuando PanelAsientos notifica que se hizo clic en "Comprar Entradas".

        // Lógica del controlador: Valida si hay asientos seleccionados.
        if (salaActual != null && !salaActual.getSelectedAsientos().isEmpty()) {
            // Prepara el mensaje de confirmación usando los datos del modelo Sala.
            String seats = "";
            // Se llama a: Sala.getSelectedAsientos()
            for (Asiento asiento : salaActual.getSelectedAsientos()) {
                // Se llama a: Asiento.getNombre()
                seats += asiento.getNombre() + " ";
            }
            // Se llama a: Sala.getTotalPrice()
            JOptionPane.showMessageDialog(panelAsientos,
                    "¡Compra Exitosa!\nPelícula: " + panelAsientos.peliculaTitulo + // La vista puede proporcionar info de display
                            "\nHora: " + panelAsientos.horaSeleccionada +
                            "\nAsientos: " + seats.trim() +
                            "\nTotal: " + String.format("$%.2f", salaActual.getTotalPrice()),
                    "Confirmación de Compra", JOptionPane.INFORMATION_MESSAGE);
            // Cierra la ventana de asientos.
            panelAsientos.dispose();
            this.salaActual = null; // Reinicia la sala actual después de la compra
        } else {
            // Si no hay asientos, muestra un mensaje de advertencia.
            JOptionPane.showMessageDialog(panelAsientos, "Por favor, selecciona al menos un asiento.", "Error de Selección", JOptionPane.WARNING_MESSAGE);
        }
    }
}