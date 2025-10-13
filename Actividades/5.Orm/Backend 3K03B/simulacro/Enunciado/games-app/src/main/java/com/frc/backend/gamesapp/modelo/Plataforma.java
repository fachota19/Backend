package com.frc.backend.gamesapp.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PLATAFORMAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAT_ID")
    private Integer id;

    @Column(name = "NOMBRE", nullable = false, unique = true, length = 255)
    private String nombre;
}
