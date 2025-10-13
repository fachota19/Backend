package com.frc.backend.modelo;

import javax.persistence.*;

@Entity
@Table(name = "DESIGNERS")
public class Designer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_designer")
    @SequenceGenerator(name = "seq_designer", sequenceName = "SEQ_DESIGNER_ID", allocationSize = 1)
    @Column(name = "ID_DESIGNER")
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true, length = 160)
    private String name;

    public Designer() {}

    public Designer(String name) {
        this.name = name;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
