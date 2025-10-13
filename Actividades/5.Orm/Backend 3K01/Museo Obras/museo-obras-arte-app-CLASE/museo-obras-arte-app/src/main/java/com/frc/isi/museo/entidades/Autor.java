package com.frc.isi.museo.entidades;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nombre;
}
