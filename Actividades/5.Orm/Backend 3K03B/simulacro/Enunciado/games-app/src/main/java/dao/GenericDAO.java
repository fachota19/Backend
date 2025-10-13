package com.frc.backend.gamesapp.dao;

import jakarta.persistence.EntityManager;
import java.util.List;

public abstract class GenericDAO<T> {
    protected final EntityManager em;
    private final Class<T> clase;

    public GenericDAO(EntityManager em, Class<T> clase) {
        this.em = em;
        this.clase = clase;
    }

    public void guardar(T entidad) {
        em.getTransaction().begin();
        em.persist(entidad);
        em.getTransaction().commit();
    }

    public T buscarPorId(Integer id) {
        return em.find(clase, id);
    }

    public List<T> listarTodos() {
        return em.createQuery("FROM " + clase.getSimpleName(), clase).getResultList();
    }
}