package ar.edu.utn.frc.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "GENEROS")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_seq")
    @SequenceGenerator(name = "gen_seq", sequenceName = "SEQ_GENEROS", allocationSize = 1)
    @Column(name = "GEN_ID")
    private Integer id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}