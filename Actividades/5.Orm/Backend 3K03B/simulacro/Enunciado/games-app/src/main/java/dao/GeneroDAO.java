package com.frc.backend.gamesapp.dao;

import com.frc.backend.gamesapp.modelo.Genero;
import jakarta.persistence.EntityManager;
import java.util.List;

public class GeneroDAO extends GenericDAO<Genero> {

    public GeneroDAO(EntityManager em) {
        super(em, Genero.class);
    }

    public Genero guardarSiNoExiste(String nombre) {
        List<Genero> existentes = em.createQuery("FROM Genero g WHERE g.nombre = :nombre", Genero.class)
                .setParameter("nombre", nombre)
                .getResultList();
        if (!existentes.isEmpty()) return existentes.get(0);

        em.getTransaction().begin();
        Genero nuevo = new Genero(null, nombre);
        em.persist(nuevo);
        em.getTransaction().commit();
        return nuevo;
    }
}