package com.frc.backend.gamesapp.dao;

import com.frc.backend.gamesapp.modelo.Plataforma;
import jakarta.persistence.EntityManager;
import java.util.List;

public class PlataformaDAO extends GenericDAO<Plataforma> {

    public PlataformaDAO(EntityManager em) {
        super(em, Plataforma.class);
    }

    public Plataforma guardarSiNoExiste(String nombre) {
        List<Plataforma> existentes = em.createQuery("FROM Plataforma p WHERE p.nombre = :nombre", Plataforma.class)
                .setParameter("nombre", nombre)
                .getResultList();
        if (!existentes.isEmpty()) return existentes.get(0);

        em.getTransaction().begin();
        Plataforma nuevo = new Plataforma(null, nombre);
        em.persist(nuevo);
        em.getTransaction().commit();
        return nuevo;
    }
}