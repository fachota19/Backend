package com.frc.isi.museo.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "obras_artisticas")
public class ObraArtistica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private Integer anio;
    private Double montoAsegurado;
    private Boolean seguroTotal;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "museo_id")
    private Museo museo;

    @ManyToOne
    @JoinColumn(name = "estilo_id")
    private EstiloArtistico estilo;

    public ObraArtistica() {}

    public ObraArtistica(String nombre, Integer anio, Double montoAsegurado, Boolean seguroTotal,
                         Autor autor, Museo museo, EstiloArtistico estilo) {
        this.nombre = nombre;
        this.anio = anio;
        this.montoAsegurado = montoAsegurado;
        this.seguroTotal = seguroTotal;
        this.autor = autor;
        this.museo = museo;
        this.estilo = estilo;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getAnio() {
        return anio;
    }

    public Double getMontoAsegurado() {
        return montoAsegurado;
    }

    public Boolean getSeguroTotal() {
        return seguroTotal;
    }

    public Autor getAutor() {
        return autor;
    }

    public Museo getMuseo() {
        return museo;
    }

    public EstiloArtistico getEstilo() {
        return estilo;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public void setMontoAsegurado(Double montoAsegurado) {
        this.montoAsegurado = montoAsegurado;
    }

    public void setSeguroTotal(Boolean seguroTotal) {
        this.seguroTotal = seguroTotal;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setMuseo(Museo museo) {
        this.museo = museo;
    }

    public void setEstilo(EstiloArtistico estilo) {
        this.estilo = estilo;
    }

    @Override
    public String toString() {
        return String.format("%s (%d) - %s - %s [%s] - $%.2f", 
            nombre, anio, autor.getNombre(), museo.getNombre(), 
            estilo.getNombre(), montoAsegurado);
    }
}
