package org.example.pelicula.view;
import org.example.pelicula.model.Pelicula;

public interface PeliculaActionListener {
    void onScheduleSelected(Pelicula pelicula, String hora);
}
