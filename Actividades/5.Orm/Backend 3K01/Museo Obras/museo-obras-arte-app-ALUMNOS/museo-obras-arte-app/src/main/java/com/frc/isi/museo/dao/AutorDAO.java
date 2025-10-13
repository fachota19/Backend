package com.frc.isi.museo.dao;

import com.frc.isi.museo.entidades.Autor;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * DAO específico para manejar autores en la base de datos.
 */
public class AutorDAO extends BaseDAO<Autor> {

    public AutorDAO(EntityManager em) {
        super(em, Autor.class);
    }

    public Autor buscarPorNombre(String nombre) {
        List<Autor> autores = em.createQuery(
            "FROM Autor a WHERE a.nombre = :nombre", Autor.class)
            .setParameter("nombre", nombre)
            .getResultList();
        return autores.isEmpty() ? null : autores.get(0);
    }
}
