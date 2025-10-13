package com.frc.backend.gamesapp.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DESARROLLADORES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Desarrollador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DESA_ID")
    private Integer id;

    @Column(name = "NOMBRE", nullable = false, unique = true, length = 255)
    private String nombre;
}
