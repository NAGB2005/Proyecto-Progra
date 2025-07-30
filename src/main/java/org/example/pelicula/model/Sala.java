package org.example.pelicula.model;
import java.util.ArrayList;
import java.util.List;

public class Sala {
    private List<Asiento> asientos;
    public final double precioTicket; // Final porque el precio del ticket de esta sala es fijo

    public Sala(double precioTicket) {
        this.precioTicket = precioTicket;
        this.asientos = new ArrayList<>();
        // Inicialización de asientos (simulando ocupación)
        boolean[] occupiedSeats = {
                false, false, true, false, false, false, true, false, false, false, // Fila A
                false, false, false, false, false, false, false, false, false, false, // Fila B
                false, true, false, false, true, false, false, false, false, false, // Fila C
                false, false, false, false, false, false, false, false, true, false, // Fila D
                false, false, false, false, false, false, false, false, false, false, // Fila E
                true, false, false, false, false, false, false, false, false, false, // Fila F
                false, false, false, false, true, false, false, false, false, false, // Fila G
                false, false, false, false, false, false, false, false, false, false  // Fila H
        };

        char rowChar = 'A';
        int seatCounter = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 1; col <= 10; col++) {
                String seatName = String.valueOf(rowChar) + col;
                // Asegurarse de que el índice no exceda el tamaño de occupiedSeats
                boolean isOccupied = (seatCounter < occupiedSeats.length) ? occupiedSeats[seatCounter++] : false;
                asientos.add(new Asiento(seatName, isOccupied));
            }
            rowChar++; // Incrementa la letra de la fila para la siguiente
        }
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public List<Asiento> getSelectedAsientos() {
        List<Asiento> selected = new ArrayList<>();
        for (Asiento asiento : asientos) {
            if (asiento.isSeleccionado()) {
                selected.add(asiento);
            }
        }
        return selected;
    }

    public double getTotalPrice() {
        return getSelectedAsientos().size() * precioTicket;
    }

    public void toggleAsientoSelection(String seatName) {
        for (Asiento asiento : asientos) {
            if (asiento.getNombre().equals(seatName)) {
                asiento.setSeleccionado(!asiento.isSeleccionado());
                return;
            }
        }
    }
}