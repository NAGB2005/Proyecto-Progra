package org.example.pelicula.model;

public class Pelicula {
    private String titulo;
    private String imagen;
    private String clasificacionedad;
    private String estado;

    public Pelicula(String titulo, String imagen, String clasificacionedad, String estado){
        this.clasificacionedad = clasificacionedad;
        this.titulo = titulo;
        this.estado = estado;
        this.imagen = imagen;
    }

    public String getTitulo() { return titulo; }
    public String getImagen() { return imagen; }
    public String getClasificacionedad() { return clasificacionedad; }
    public String getEstado() { return estado; }
}
