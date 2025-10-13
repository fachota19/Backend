package com.frc.backend.gamesapp.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "JUEGOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JUEGO_ID")
    private Integer id;

    @Column(name = "TITULO", nullable = false, length = 255)
    private String titulo;

    @Column(name = "FECHA_LANZAMIENTO")
    private Integer fechaLanzamiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENERO_ID", nullable = false)
    private Genero genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLATAFORMA_ID", nullable = false)
    private Plataforma plataforma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DESARROLLADOR_ID", nullable = false)
    private Desarrollador desarrollador;

    @Enumerated(EnumType.STRING)
    @Column(name = "CLASIFICACION_ESRB", length = 6)
    private ClasificacionESRB clasificacionESRB;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "JUEGOS_FINALIZADOS")
    private Integer juegosFinalizados;

    @Column(name = "JUGANDO")
    private Integer jugando;

    @Column(name = "RESUMEN", columnDefinition = "CLOB")
    private String resumen;
}
