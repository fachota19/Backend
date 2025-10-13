package ar.edu.utn.frc.dao;

import ar.edu.utn.frc.domain.Genero;
import ar.edu.utn.frc.infrastructure.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class GeneroDao extends AbstractJpaDao<Genero, Integer> {
    public GeneroDao() { super(Genero.class); }

    public Optional<Genero> findByNombreIgnoreCase(String nombre) {
        EntityManager em = JpaUtil.em();
        try {
            var q = em.createQuery(
                "select g from Genero g where lower(g.nombre) = lower(:n)", Genero.class);
            q.setParameter("n", nombre);
            return q.getResultStream().findFirst();
        } finally {
            em.close();
        }
    }
}
