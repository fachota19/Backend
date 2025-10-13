package ar.edu.utn.frc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "DIRECTOR")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre",unique = true, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Pelicula> peliculas;

    public Director(String nombre){
        this.nombre = nombre;
    }
}
