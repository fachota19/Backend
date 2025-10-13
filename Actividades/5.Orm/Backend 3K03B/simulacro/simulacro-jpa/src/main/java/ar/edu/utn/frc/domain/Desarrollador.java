package ar.edu.utn.frc.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "DESARROLLADORES")
public class Desarrollador {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "desa_seq")
    @SequenceGenerator(name = "desa_seq", sequenceName = "SEQ_DESARROLLADORES", allocationSize = 1)
    @Column(name = "DESA_ID")
    private Integer id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}