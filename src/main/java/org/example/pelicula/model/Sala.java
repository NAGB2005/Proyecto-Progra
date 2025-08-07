package org.example.pelicula.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>Representa una sala de cine, gestionando el estado de todos sus asientos.</p>
 * <p>Crea un mapa de asientos basado en los que ya están ocupados, gestiona la selección
 * de asientos por parte del usuario y calcula el precio total de la compra.</p>
 */
public class Sala {
    private List<Asiento> asientos;
    public final double precioTicket;

    /**
     * Constructor para la clase Sala.
     * @param precioTicket El precio base de un ticket para esta sala.
     * @param asientosOcupados Una lista de nombres de asientos que ya están ocupados.
     */
    public Sala(double precioTicket, List<String> asientosOcupados) {
        this.precioTicket = precioTicket;
        this.asientos = new ArrayList<>();

        char rowChar = 'A';
        for (int row = 0; row < 8; row++) {
            for (int col = 1; col <= 10; col++) {
                String seatName = String.valueOf(rowChar) + col;
                boolean isOccupied = asientosOcupados.contains(seatName);
                asientos.add(new Asiento(seatName, isOccupied));
            }
            rowChar++;
        }
    }

    /**
     * Obtiene una lista no modificable de todos los asientos de la sala.
     * @return Una lista inmutable de todos los asientos.
     */
    public List<Asiento> getAsientos() {
        return Collections.unmodifiableList(asientos);
    }

    /**
     * Obtiene una lista de los asientos que han sido seleccionados por el usuario.
     * @return Una lista con los asientos seleccionados.
     */
    public List<Asiento> getSelectedAsientos() {
        List<Asiento> selected = new ArrayList<>();
        for (Asiento asiento : asientos) {
            if (asiento.isSeleccionado()) {
                selected.add(asiento);
            }
        }
        return selected;
    }

    /**
     * Calcula el precio total de la compra basándose en la cantidad de asientos seleccionados
     * y el precio del ticket.
     * @return El precio total de los tickets seleccionados.
     */
    public double getTotalPrice() {
        return getSelectedAsientos().size() * precioTicket;
    }

    /**
     * Cambia el estado de selección de un asiento (de seleccionado a no seleccionado y viceversa).
     * @param seatName El nombre del asiento a alternar.
     */
    public void toggleAsientoSelection(String seatName) {
        for (Asiento asiento : asientos) {
            if (asiento.getNombre().equals(seatName) && !asiento.isOcupado()) {
                asiento.setSeleccionado(!asiento.isSeleccionado());
                return;
            }
        }
    }
}