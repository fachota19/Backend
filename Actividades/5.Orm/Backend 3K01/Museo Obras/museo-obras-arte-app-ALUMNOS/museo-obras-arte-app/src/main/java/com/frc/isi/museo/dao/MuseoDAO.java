package com.frc.isi.museo.dao;

import com.frc.isi.museo.entidades.Museo;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * DAO para gestionar entidades Museo.
 */
public class MuseoDAO extends BaseDAO<Museo> {

    public MuseoDAO(EntityManager em) {
        super(em, Museo.class);
    }

    public Museo buscarPorNombre(String nombre) {
        List<Museo> museos = em.createQuery(
            "FROM Museo m WHERE m.nombre = :nombre", Museo.class)
            .setParameter("nombre", nombre)
            .getResultList();
        return museos.isEmpty() ? null : museos.get(0);
    }
}
