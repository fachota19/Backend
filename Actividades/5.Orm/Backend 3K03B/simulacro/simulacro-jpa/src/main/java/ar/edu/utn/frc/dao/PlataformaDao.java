package ar.edu.utn.frc.dao;

import ar.edu.utn.frc.domain.Plataforma;
import ar.edu.utn.frc.infrastructure.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class PlataformaDao extends AbstractJpaDao<Plataforma, Integer> {
    public PlataformaDao() { super(Plataforma.class); }

    public Optional<Plataforma> findByNombreIgnoreCase(String nombre) {
        EntityManager em = JpaUtil.em();
        try {
            var q = em.createQuery(
                "select p from Plataforma p where lower(p.nombre) = lower(:n)", Plataforma.class);
            q.setParameter("n", nombre);
            return q.getResultStream().findFirst();
        } finally {
            em.close();
        }
    }
}
