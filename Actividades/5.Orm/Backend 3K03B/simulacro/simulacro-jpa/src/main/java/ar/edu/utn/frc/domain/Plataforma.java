package ar.edu.utn.frc.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "PLATAFORMAS")
public class Plataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plat_seq")
    @SequenceGenerator(name = "plat_seq", sequenceName = "SEQ_PLATAFORMAS", allocationSize = 1)
    @Column(name = "PLAT_ID")
    private Integer id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}