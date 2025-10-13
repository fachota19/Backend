package ar.edu.utn.frc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "GENERO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre", unique = true, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "genero", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Pelicula> peliculas;

    public Genero(String nombre){
        this.nombre = nombre;
    }
}
