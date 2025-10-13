package com.frc.backend.gamesapp.dao;

import com.frc.backend.gamesapp.modelo.Desarrollador;
import jakarta.persistence.EntityManager;
import java.util.List;

public class DesarrolladorDAO extends GenericDAO<Desarrollador> {

    public DesarrolladorDAO(EntityManager em) {
        super(em, Desarrollador.class);
    }

    public Desarrollador guardarSiNoExiste(String nombre) {
        List<Desarrollador> existentes = em.createQuery("FROM Desarrollador d WHERE d.nombre = :nombre", Desarrollador.class)
                .setParameter("nombre", nombre)
                .getResultList();
        if (!existentes.isEmpty()) return existentes.get(0);

        em.getTransaction().begin();
        Desarrollador nuevo = new Desarrollador(null, nombre);
        em.persist(nuevo);
        em.getTransaction().commit();
        return nuevo;
    }
}
