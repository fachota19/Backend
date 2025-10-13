package com.frc.backend.modelo;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORIES")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_category")
    @SequenceGenerator(name = "seq_category", sequenceName = "SEQ_CATEGORY_ID", allocationSize = 1)
    @Column(name = "ID_CATEGORY")
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true, length = 120)
    private String name;

    public Category() {}

    public Category(String name) {
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
