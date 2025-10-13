package ar.edu.utnfrc.backend.model;

import java.time.LocalDate;

public class Pelicula {
    private String titulo;
    private String director;
    private String genero;
    private double precio;
    private LocalDate fechaEstreno;

    public Pelicula(String titulo, String director, String genero, double precio, LocalDate fechaEstreno) {
        this.titulo = titulo;
        this.director = director;
        this.genero = genero;
        this.precio = precio;
        this.fechaEstreno = fechaEstreno;
    }

    public String getTitulo() { return titulo; }
    public String getDirector() { return director; }
    public String getGenero() { return genero; }
    public double getPrecio() { return precio; }
    public LocalDate getFechaEstreno() { return fechaEstreno; }

    @Override
    public String toString() {
        return String.format("%-15s | %-20s | %-12s | $%-6.2f | %s",
                titulo, director, genero, precio, fechaEstreno);
    }
}
