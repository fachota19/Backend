package com.frc.isi.museo.dao;

import com.frc.isi.museo.entidades.ObraArtistica;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * DAO específico para las obras artísticas.
 */
public class ObraArtisticaDAO extends BaseDAO<ObraArtistica> {

    public ObraArtisticaDAO(EntityManager em) {
        super(em, ObraArtistica.class);
    }

    public List<ObraArtistica> buscarPorMuseo(String nombreMuseo) {
        return em.createQuery(
            "SELECT o FROM ObraArtistica o WHERE o.museo.nombre = :nombre", ObraArtistica.class)
            .setParameter("nombre", nombreMuseo)
            .getResultList();
    }

    public List<ObraArtistica> buscarPorAutor(String nombreAutor) {
        return em.createQuery(
            "SELECT o FROM ObraArtistica o WHERE o.autor.nombre = :nombre", ObraArtistica.class)
            .setParameter("nombre", nombreAutor)
            .getResultList();
    }

    public List<ObraArtistica> buscarPorEstilo(String nombreEstilo) {
        return em.createQuery(
            "SELECT o FROM ObraArtistica o WHERE o.estilo.nombre = :nombre", ObraArtistica.class)
            .setParameter("nombre", nombreEstilo)
            .getResultList();
    }
}