package org.example.pelicula.model;

/**
 * Representa un asiento individual en una sala de cine.
 * Contiene su nombre, estado de ocupación y estado de selección.
 */
public class Asiento {
    private String nombre;
    private boolean ocupado;
    private boolean seleccionado;

    /**
     * Constructor para un asiento.
     * @param nombre El nombre del asiento (ej. "A1").
     * @param ocupado Un valor booleano que indica si el asiento ya está ocupado.
     */
    public Asiento(String nombre, boolean ocupado) {
        this.nombre = nombre;
        this.ocupado = ocupado;
        this.seleccionado = false;
    }

    /**
     * Obtiene el nombre del asiento.
     * @return El nombre del asiento.
     */
    public String getNombre() { return nombre; }

    /**
     * Comprueba si el asiento está ocupado.
     * @return true si el asiento está ocupado, de lo contrario false.
     */
    public boolean isOcupado() { return ocupado; }

    /**
     * Comprueba si el asiento está seleccionado por el usuario.
     * @return true si el asiento está seleccionado, de lo contrario false.
     */
    public boolean isSeleccionado() { return seleccionado; }

    /**
     * Establece el estado de selección del asiento.
     * Solo se puede seleccionar si no está ocupado.
     * @param seleccionado true para seleccionar, false para deseleccionar.
     */
    public void setSeleccionado(boolean seleccionado) {
        if (!ocupado) {
            this.seleccionado = seleccionado;
        }
    }
}