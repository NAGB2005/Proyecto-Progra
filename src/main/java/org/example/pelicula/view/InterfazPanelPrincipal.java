package org.example.pelicula.view;

import org.example.pelicula.model.Pelicula;

public interface InterfazPanelPrincipal {
    void onPeliculaSelected(Pelicula pelicula);
    void onScheduleSelected(Pelicula pelicula, String horario);
}