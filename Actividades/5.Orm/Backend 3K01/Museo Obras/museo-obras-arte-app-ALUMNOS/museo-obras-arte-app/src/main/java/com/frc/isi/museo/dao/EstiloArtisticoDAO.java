package com.frc.isi.museo.dao;

import com.frc.isi.museo.entidades.EstiloArtistico;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * DAO para gestionar los estilos artísticos de las obras.
 */
public class EstiloArtisticoDAO extends BaseDAO<EstiloArtistico> {

    public EstiloArtisticoDAO(EntityManager em) {
        super(em, EstiloArtistico.class);
    }

    public EstiloArtistico buscarPorNombre(String nombre) {
        List<EstiloArtistico> estilos = em.createQuery(
            "FROM EstiloArtistico e WHERE e.nombre = :nombre", EstiloArtistico.class)
            .setParameter("nombre", nombre)
            .getResultList();
        return estilos.isEmpty() ? null : estilos.get(0);
    }
}
