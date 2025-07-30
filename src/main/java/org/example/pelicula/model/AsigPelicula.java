package org.example.pelicula.model;
import java.util.ArrayList;
import java.util.List;

public class AsigPelicula {
    public List<Pelicula> getPeliculas(){
        List<Pelicula> peliculas = new ArrayList<>();
        peliculas.add(new Pelicula("Infinity Casttle", "InfinityCaslte.jpg", "+13", "Pre_venta"));
        peliculas.add(new Pelicula("WALKING DEAD\n DEAD CITY", "The_walking_dead.jpg", "+15", "Venta"));
        peliculas.add(new Pelicula("The Purge", "The_purge.jpg", "+15", "Venta"));
        peliculas.add(new Pelicula("Amierican Milf", "America_Milf.jpg", "+12", "Venta"));
        return peliculas;
    }
}