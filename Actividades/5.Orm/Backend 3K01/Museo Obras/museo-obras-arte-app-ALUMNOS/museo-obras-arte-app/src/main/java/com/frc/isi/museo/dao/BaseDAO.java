package com.frc.isi.museo.dao;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * Clase base genérica para operaciones CRUD básicas con JPA.
 * Todas las clases DAO específicas heredan de esta.
 */
public abstract class BaseDAO<T> {

    protected EntityManager em;
    private final Class<T> entityClass;

    public BaseDAO(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    public void guardar(T entidad) {
        em.getTransaction().begin();
        em.persist(entidad);
        em.getTransaction().commit();
    }

    public void actualizar(T entidad) {
        em.getTransaction().begin();
        em.merge(entidad);
        em.getTransaction().commit();
    }

    public void eliminar(T entidad) {
        em.getTransaction().begin();
        em.remove(entidad);
        em.getTransaction().commit();
    }

    public T buscarPorId(Integer id) {
        return em.find(entityClass, id);
    }

    public List<T> listar() {
        return em.createQuery("FROM " + entityClass.getSimpleName(), entityClass).getResultList();
    }
}