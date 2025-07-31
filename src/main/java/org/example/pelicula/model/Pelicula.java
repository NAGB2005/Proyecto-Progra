package org.example.pelicula.model;

import java.util.List;

public class Pelicula {
    private String titulo;
    private String imagen;
    private String clasificacionedad;
    private String estado;
    private String duracion;
    private List<String> horarios;
    private double precioTicket;
    private List<String> asientosOcupados;

    public Pelicula(String titulo, String imagen, String clasificacionedad, String estado, String duracion, List<String> horarios, double precioTicket, List<String> asientosOcupados) {
        this.titulo = titulo;
        this.imagen = imagen;
        this.clasificacionedad = clasificacionedad;
        this.estado = estado;
        this.duracion = duracion;
        this.horarios = horarios;
        this.precioTicket = precioTicket;
        this.asientosOcupados = asientosOcupados;
    }

    public String getTitulo() { return titulo; }
    public String getImagen() { return imagen; }
    public String getClasificacionedad() { return clasificacionedad; }
    public String getEstado() { return estado; }
    public String getDuracion() { return duracion; }
    public List<String> getHorarios() { return horarios; }
    public double getPrecioTicket() { return precioTicket; }
    public List<String> getAsientosOcupados() { return asientosOcupados; }
}