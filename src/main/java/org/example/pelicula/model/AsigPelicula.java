package org.example.pelicula.model;

import java.io.*;
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
                    if (line.trim().isEmpty()) continue; // Ignorar líneas en blanco
                    Pelicula p = parsearLineaPelicula(line);
                    if (p != null) {
                        peliculasCargadas.add(p);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al leer el archivo de texto: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Archivo " + FILE_PATH + " no encontrado o vacío. Creando archivo por defecto.");
            peliculasCargadas = crearPeliculasPorDefecto();
            guardarPeliculas();
        }
        return peliculasCargadas;
    }

    // Método corregido para validar la longitud de las partes
    private Pelicula parsearLineaPelicula(String line) {
        String[] partes = line.split("-");

        // Validar que la línea tenga el formato correcto
        if (partes.length < 5) {
            System.err.println("Error: Formato de línea incorrecto en peliculas.txt -> " + line);
            return null;
        }

        String titulo = partes[0];
        String imagen = partes[1];
        String clasificacionedad = partes[2];
        String estado = partes[3];
        String duracion = partes[4];

        List<Pelicula.Funcion> funciones = new ArrayList<>();
        if (partes.length > 5 && !partes[5].isEmpty()) {
            String[] funcionesStr = partes[5].split("\\|");
            for (String fStr : funcionesStr) {
                String[] fPartes = fStr.split(":");
                if (fPartes.length >= 2) {
                    String horario = fPartes[0];
                    double precio = Double.parseDouble(fPartes[1]);

                    List<String> asientosOcupados = new ArrayList<>();
                    if (fPartes.length > 2 && !fPartes[2].isEmpty()) {
                        asientosOcupados = Arrays.asList(fPartes[2].split(","));
                    }
                    funciones.add(new Pelicula.Funcion(horario, precio, asientosOcupados));
                }
            }
        }
        return new Pelicula(titulo, imagen, clasificacionedad, estado, duracion, funciones);
    }

    private List<Pelicula> crearPeliculasPorDefecto() {
        List<Pelicula> peliculas = new ArrayList<>();
        peliculas.add(new Pelicula(
                "Infinity Castle", "InfinityCaslte.jpg", "+13", "Pre_venta", "1h 50min",
                Arrays.asList(
                        new Pelicula.Funcion("14:30", 7.50, Arrays.asList("A3", "A7", "C2", "C5")),
                        new Pelicula.Funcion("18:00", 7.50, Arrays.asList("B1", "F1", "G5")),
                        new Pelicula.Funcion("21:00", 7.50, Arrays.asList("H1", "H2", "H3"))
                )
        ));
        peliculas.add(new Pelicula(
                "WALKING DEAD DEAD CITY", "The_walking_dead.jpg", "+15", "Venta", "1h 45min",
                Arrays.asList(
                        new Pelicula.Funcion("15:00", 8.00, Arrays.asList("B1", "B2", "B3", "B4")),
                        new Pelicula.Funcion("19:00", 8.00, Arrays.asList("E10", "F9", "F10")),
                        new Pelicula.Funcion("22:30", 8.00, Arrays.asList("H1", "H2"))
                )
        ));
        peliculas.add(new Pelicula(
                "The Purge", "The_purge.jpg", "+15", "Venta", "1h 35min",
                Arrays.asList(
                        new Pelicula.Funcion("16:00", 7.50, new ArrayList<>()),
                        new Pelicula.Funcion("20:30", 7.50, Arrays.asList("A1", "A2"))
                )
        ));
        this.peliculas = peliculas;
        return peliculas;
    }

    public void guardarPeliculas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Pelicula p : this.peliculas) {
                StringBuilder line = new StringBuilder();
                line.append(String.format("%s~%s~%s~%s~%s~",
                        p.getTitulo(), p.getImagen(), p.getClasificacionedad(), p.getEstado(), p.getDuracion()));

                List<String> funcionesStr = new ArrayList<>();
                for (Pelicula.Funcion f : p.getFunciones()) {
                    String asientosStr = String.join(",", f.getAsientosOcupados());
                    funcionesStr.add(String.format("%s:%.2f:%s", f.getHorario(), f.getPrecioTicket(), asientosStr));
                }
                line.append(String.join("|", funcionesStr));

                writer.write(line.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo de texto: " + e.getMessage());
            e.printStackTrace();
        }
    }
}