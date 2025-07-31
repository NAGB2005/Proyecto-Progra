package org.example.pelicula.model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AsigPelicula {

    private List<Pelicula> peliculas;
    private static final String FILE_PATH = "peliculas.txt";

    public AsigPelicula() {
        this.peliculas = cargarOCrearPeliculas();
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    private List<Pelicula> cargarOCrearPeliculas() {
        File file = new File(FILE_PATH);
        List<Pelicula> peliculasCargadas = new ArrayList<>();

        if (file.exists() && file.length() > 0) {
            System.out.println("Archivo " + FILE_PATH + " encontrado. Cargando películas...");
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    peliculasCargadas.add(parsearLineaPelicula(line));
                }
            } catch (IOException e) {
                System.err.println("Error al leer el archivo de texto: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Archivo " + FILE_PATH + " no encontrado o vacío. Creando archivo por defecto.");
            peliculasCargadas = crearPeliculasPorDefecto();
            guardarPeliculas(peliculasCargadas);
        }
        return peliculasCargadas;
    }

    // Método para convertir una línea de texto a un objeto Pelicula
    private Pelicula parsearLineaPelicula(String line) {
        String[] partes = line.split(",");
        String titulo = partes[0];
        String imagen = partes[1];
        String clasificacionedad = partes[2];
        String estado = partes[3];
        String duracion = partes[4];
        double precioTicket = Double.parseDouble(partes[5]);

        List<String> horarios = new ArrayList<>();
        if (partes.length > 6 && !partes[6].isEmpty()) {
            horarios = Arrays.asList(partes[6].split("\\|"));
        }

        List<String> asientosOcupados = new ArrayList<>();
        if (partes.length > 7 && !partes[7].isEmpty()) {
            asientosOcupados = Arrays.asList(partes[7].split("\\|"));
        }

        return new Pelicula(titulo, imagen, clasificacionedad, estado, duracion, horarios, precioTicket, asientosOcupados);
    }

    // Método para crear una lista de películas por defecto
    private List<Pelicula> crearPeliculasPorDefecto() {
        List<Pelicula> peliculas = new ArrayList<>();
        peliculas.add(new Pelicula(
                "Infinity Castle", "InfinityCaslte.jpg", "+13", "Pre_venta",
                "1h 50min", Arrays.asList("14:30", "18:00", "21:00"), 7.50,
                Arrays.asList("A3", "A7", "C2", "C5", "D9", "F1", "G5")
        ));
        peliculas.add(new Pelicula(
                "WALKING DEAD DEAD CITY", "The_walking_dead.jpg", "+15", "Venta",
                "1h 45min", Arrays.asList("15:00", "19:00", "22:30"), 8.00,
                Arrays.asList("B1", "B2", "B3", "B4", "E10", "F9", "F10", "H1", "H2")
        ));
        peliculas.add(new Pelicula(
                "The Purge", "The_purge.jpg", "+15", "Venta",
                "1h 35min", Arrays.asList("16:00", "20:30"), 7.50,
                Arrays.asList()
        ));
        return peliculas;
    }

    // Método para guardar una lista de películas en el archivo de texto
    private void guardarPeliculas(List<Pelicula> peliculas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Pelicula p : peliculas) {
                String horariosStr = String.join("|", p.getHorarios());
                String asientosStr = String.join("|", p.getAsientosOcupados());

                String line = String.format("%s,%s,%s,%s,%s,%.2f,%s,%s",
                        p.getTitulo(), p.getImagen(), p.getClasificacionedad(), p.getEstado(),
                        p.getDuracion(), p.getPrecioTicket(), horariosStr, asientosStr);

                writer.write(line);
                writer.newLine();
            }
            System.out.println("Archivo " + FILE_PATH + " creado con éxito.");
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo de texto: " + e.getMessage());
            e.printStackTrace();
        }
    }
}