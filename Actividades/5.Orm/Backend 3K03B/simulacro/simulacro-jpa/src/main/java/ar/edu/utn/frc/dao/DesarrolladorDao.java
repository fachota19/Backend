package ar.edu.utn.frc.dao;

import ar.edu.utn.frc.domain.Desarrollador;
import ar.edu.utn.frc.infrastructure.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class DesarrolladorDao extends AbstractJpaDao<Desarrollador, Integer> {
    public DesarrolladorDao() { super(Desarrollador.class); }

    public Optional<Desarrollador> findByNombreIgnoreCase(String nombre) {
        EntityManager em = JpaUtil.em();
        try {
            var q = em.createQuery(
                "select d from Desarrollador d where lower(d.nombre) = lower(:n)", Desarrollador.class);
            q.setParameter("n", nombre);
            return q.getResultStream().findFirst();
        } finally {
            em.close();
        }
    }
}