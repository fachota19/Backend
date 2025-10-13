package ar.edu.utn.frc.dao;

import ar.edu.utn.frc.domain.ClasificacionEsrb;
import ar.edu.utn.frc.domain.Juego;
import ar.edu.utn.frc.infrastructure.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class JuegoDao extends AbstractJpaDao<Juego, Integer> {
    public JuegoDao() { super(Juego.class); }

    public List<Juego> findByTituloContainsIgnoreCase(String fragmento) {
        EntityManager em = JpaUtil.em();
        try {
            var q = em.createQuery(
                "select j from Juego j where lower(j.titulo) like lower(:frag) order by j.titulo",
                Juego.class);
            q.setParameter("frag", "%" + fragmento + "%");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Juego> findByGeneroNombre(String genero) {
        EntityManager em = JpaUtil.em();
        try {
            var q = em.createQuery(
                "select j from Juego j " +
                " join j.genero g " +
                " where lower(g.nombre) = lower(:gn) " +
                " order by j.titulo", Juego.class);
            q.setParameter("gn", genero);
            return q.getResultList();
        } finally { em.close(); }
    }

    public List<Juego> findByDesarrolladorNombre(String dev) {
        EntityManager em = JpaUtil.em();
        try {
            var q = em.createQuery(
                "select j from Juego j " +
                " join j.desarrollador d " +
                " where lower(d.nombre) = lower(:dn) " +
                " order by j.titulo", Juego.class);
            q.setParameter("dn", dev);
            return q.getResultList();
        } finally { em.close(); }
    }

    public List<Juego> findByPlataformaNombre(String plat) {
        EntityManager em = JpaUtil.em();
        try {
            var q = em.createQuery(
                "select j from Juego j " +
                " join j.plataforma p " +
                " where lower(p.nombre) = lower(:pn) " +
                " order by j.titulo", Juego.class);
            q.setParameter("pn", plat);
            return q.getResultList();
        } finally { em.close(); }
    }

    public List<Juego> findByClasificacion(ClasificacionEsrb esrb) {
        EntityManager em = JpaUtil.em();
        try {
            var q = em.createQuery(
                "select j from Juego j where j.clasificacion = :esrb order by j.titulo",
                Juego.class);
            q.setParameter("esrb", esrb);
            return q.getResultList();
        } finally { em.close(); }
    }

    // Ejemplo combinado: plataforma + ESRB + paginado
    public List<Juego> findByPlataformaYEsrb(String plataforma, ClasificacionEsrb esrb, int page, int size) {
        EntityManager em = JpaUtil.em();
        try {
            var q = em.createQuery(
                "select j from Juego j " +
                " join j.plataforma p " +
                " where lower(p.nombre) = lower(:pn) and j.clasificacion = :esrb " +
                " order by j.titulo", Juego.class);
            q.setParameter("pn", plataforma);
            q.setParameter("esrb", esrb);
            q.setFirstResult(Math.max(0, page) * Math.max(1, size));
            q.setMaxResults(Math.max(1, size));
            return q.getResultList();
        } finally { em.close(); }
    }
}
