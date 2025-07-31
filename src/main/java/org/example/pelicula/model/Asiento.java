package org.example.pelicula.model;

public class Asiento {
    private String nombre;
    private boolean ocupado;
    private boolean seleccionado;

    public Asiento(String nombre, boolean ocupado) {
        this.nombre = nombre;
        this.ocupado = ocupado;
        this.seleccionado = false;
    }

    public String getNombre() { return nombre; }
    public boolean isOcupado() { return ocupado; }
    public boolean isSeleccionado() { return seleccionado; }

    public void setSeleccionado(boolean seleccionado) {
        if (!ocupado) {
            this.seleccionado = seleccionado;
        }
    }
}