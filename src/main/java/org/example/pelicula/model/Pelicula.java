package org.example.pelicula.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Representa una película con sus atributos principales y las funciones disponibles.
 * Incluye una clase interna para gestionar la información de cada función.
 */
public class Pelicula {
    private String titulo;
    private String imagen;
    private String clasificacionedad;
    private String estado;
    private String duracion;
    private List<Funcion> funciones;

    /**
     * Constructor para la clase Pelicula.
     * @param titulo El título de la película.
     * @param imagen El nombre del archivo de imagen del póster.
     * @param clasificacionedad La clasificación por edad.
     * @param estado El estado actual de la película (ej. "Venta", "Pre_venta", "Estreno").
     * @param duracion La duración de la película.
     * @param funciones Una lista de las funciones disponibles para la película.
     */
    public Pelicula(String titulo, String imagen, String clasificacionedad, String estado, String duracion, List<Funcion> funciones) {
        this.titulo = titulo;
        this.imagen = imagen;
        this.clasificacionedad = clasificacionedad;
        this.estado = estado;
        this.duracion = duracion;
        this.funciones = new ArrayList<>(funciones);
    }

    /**
     * Clase interna que representa una función de cine.
     * Contiene el horario, el precio del ticket y los asientos que ya están ocupados.
     */
    public static class Funcion {
        private String horario;
        private double precioTicket;
        private List<String> asientosOcupados;

        /**
         * Constructor para la clase Funcion.
         * @param horario El horario de la función (ej. "14:30").
         * @param precioTicket El precio del ticket.
         * @param asientosOcupados Una lista de nombres de asientos que ya están ocupados.
         */
        public Funcion(String horario, double precioTicket, List<String> asientosOcupados) {
            this.horario = horario;
            this.precioTicket = precioTicket;
            this.asientosOcupados = new ArrayList<>(asientosOcupados);
        }

        /**
         * Obtiene el horario de la función.
         * @return El horario como una cadena de texto.
         */
        public String getHorario() {
            return horario;
        }

        /**
         * Obtiene el precio del ticket.
         * @return El precio del ticket.
         */
        public double getPrecioTicket() {
            return precioTicket;
        }

        /**
         * Obtiene una copia de la lista de asientos ocupados.
         * @return Una nueva lista con los nombres de los asientos ocupados.
         */
        public List<String> getAsientosOcupados() {
            return new ArrayList<>(asientosOcupados);
        }

        /**
         * Establece una nueva lista de asientos ocupados, sobrescribiendo la anterior.
         * @param asientosOcupados La nueva lista de asientos ocupados.
         */
        public void setAsientosOcupados(List<String> asientosOcupados) {
            this.asientosOcupados = new ArrayList<>(asientosOcupados);
        }
    }

    /**
     * Obtiene el título de la película.
     * @return El título de la película.
     */
    public String getTitulo() { return titulo; }

    /**
     * Obtiene el nombre del archivo de imagen.
     * @return El nombre del archivo de imagen.
     */
    public String getImagen() { return imagen; }

    /**
     * Obtiene la clasificación por edad.
     * @return La clasificación por edad.
     */
    public String getClasificacionedad() { return clasificacionedad; }

    /**
     * Obtiene el estado de la película.
     * @return El estado de la película.
     */
    public String getEstado() { return estado; }

    /**
     * Obtiene la duración de la película.
     * @return La duración de la película.
     */
    public String getDuracion() { return duracion; }

    /**
     * Obtiene una lista no modificable de las funciones de la película.
     * @return Una lista inmutable de las funciones.
     */
    public List<Funcion> getFunciones() {
        return Collections.unmodifiableList(funciones);
    }
}