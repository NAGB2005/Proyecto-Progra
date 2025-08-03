package org.example.pelicula.model;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private List<Asiento> asientos;
    public final double precioTicket;

    public Sala(double precioTicket, List<String> asientosOcupados) {
        this.precioTicket = precioTicket;
        this.asientos = new ArrayList<>();

        char rowChar = 'A';
        int asientoIndex = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 1; col <= 10; col++) {
                String seatName = String.valueOf(rowChar) + col;
                boolean isOccupied = asientosOcupados.contains(seatName);
                asientos.add(new Asiento(seatName, isOccupied));
                asientoIndex++;
            }
            rowChar++;
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
            if (asiento.getNombre().equals(seatName) && !asiento.isOcupado()) {
                asiento.setSeleccionado(!asiento.isSeleccionado());
                return;
            }
        }
    }
}