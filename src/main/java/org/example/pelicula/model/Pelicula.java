package org.example.pelicula.model;

import java.util.List;

public class Pelicula {
    private String titulo;
    private String imagen;
    private String clasificacionedad;
    private String estado;
    private String duracion;
    private List<Funcion> funciones;

    public Pelicula(String titulo, String imagen, String clasificacionedad, String estado, String duracion, List<Funcion> funciones) {
        this.titulo = titulo;
        this.imagen = imagen;
        this.clasificacionedad = clasificacionedad;
        this.estado = estado;
        this.duracion = duracion;
        this.funciones = funciones;
    }

    public static class Funcion {
        private String horario;
        private double precioTicket;
        private List<String> asientosOcupados;

        public Funcion(String horario, double precioTicket, List<String> asientosOcupados) {
            this.horario = horario;
            this.precioTicket = precioTicket;
            this.asientosOcupados = asientosOcupados;
        }

        public String getHorario() {
            return horario;
        }

        public double getPrecioTicket() {
            return precioTicket;
        }

        public List<String> getAsientosOcupados() {
            return asientosOcupados;
        }

        public void setAsientosOcupados(List<String> asientosOcupados) {
            this.asientosOcupados = asientosOcupados;
        }
    }

    public String getTitulo() { return titulo; }
    public String getImagen() { return imagen; }
    public String getClasificacionedad() { return clasificacionedad; }
    public String getEstado() { return estado; }
    public String getDuracion() { return duracion; }
    public List<Funcion> getFunciones() { return funciones; }
}