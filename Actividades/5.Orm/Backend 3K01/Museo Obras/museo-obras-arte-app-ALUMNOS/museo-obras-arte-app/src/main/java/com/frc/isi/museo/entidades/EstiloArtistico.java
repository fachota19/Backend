package com.frc.isi.museo.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "estilos_artistico")
public class EstiloArtistico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    public EstiloArtistico() {}

    public EstiloArtistico(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
