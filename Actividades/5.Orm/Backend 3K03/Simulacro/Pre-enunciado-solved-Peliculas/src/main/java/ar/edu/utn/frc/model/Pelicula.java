package ar.edu.utn.frc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "PELICULA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo",nullable = false)
    private String titulo;

    @Column(name = "fecha_estreno")
    private LocalDate fechaEstreno;

    @Column(name = "precio_base_alquiler")
    private double precioBaseAlquiler;

    private Clasificacion clasificacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genero_id")
    private Genero genero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "director_id")
    private Director director;


    private double calcularPrecioAlquiler(){
        double precioFinal = this.precioBaseAlquiler;
        LocalDate fechaActual = LocalDate.now();
        long dias = ChronoUnit.DAYS.between(this.fechaEstreno, fechaActual);
        if(dias<= 365 ){
            precioFinal = precioFinal * 1.25;
        }
        if (this.clasificacion == Clasificacion.ADULTOS_18 || this.clasificacion == Clasificacion.ADOLESCENTES_13) {
            precioFinal *= 1.05; // Aumenta un 5% adicional para estas clasificaciones
        }
        return precioFinal;

    }


}
