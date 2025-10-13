package com.frc.backend.modelo;

import javax.persistence.*;

@Entity
@Table(name = "PUBLISHERS")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_publisher")
    @SequenceGenerator(name = "seq_publisher", sequenceName = "SEQ_PUBLISHER_ID", allocationSize = 1)
    @Column(name = "ID_PUBLISHER")
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true, length = 160)
    private String name;

    public Publisher() {}

    public Publisher(String name) {
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
