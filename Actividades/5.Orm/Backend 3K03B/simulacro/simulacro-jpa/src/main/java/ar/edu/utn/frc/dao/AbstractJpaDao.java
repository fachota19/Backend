package ar.edu.utn.frc.dao;

import ar.edu.utn.frc.infrastructure.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public abstract class AbstractJpaDao<T, ID> implements CrudDao<T, ID> {
    private final Class<T> entityClass;

    protected AbstractJpaDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T save(T entity) {
        EntityManager em = JpaUtil.em();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public T update(T entity) {
        EntityManager em = JpaUtil.em();
        try {
            em.getTransaction().begin();
            T merged = em.merge(entity);
            em.getTransaction().commit();
            return merged;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteById(ID id) {
        EntityManager em = JpaUtil.em();
        try {
            em.getTransaction().begin();
            T ref = em.find(entityClass, id);
            if (ref == null) {
                em.getTransaction().rollback();
                return false;
            }
            em.remove(ref);
            em.getTransaction().commit();
            return true;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        EntityManager em = JpaUtil.em();
        try {
            return Optional.ofNullable(em.find(entityClass, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> findAll() {
        EntityManager em = JpaUtil.em();
        try {
            String jpql = "select e from " + entityClass.getSimpleName() + " e";
            TypedQuery<T> q = em.createQuery(jpql, entityClass);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> findAll(int page, int size) {
        EntityManager em = JpaUtil.em();
        try {
            String jpql = "select e from " + entityClass.getSimpleName() + " e";
            TypedQuery<T> q = em.createQuery(jpql, entityClass);
            q.setFirstResult(Math.max(0, page) * Math.max(1, size));
            q.setMaxResults(Math.max(1, size));
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}