package com.frc.isi.museo.app;

import com.frc.isi.museo.entidades.ObraArtistica;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ConsultasJPQL {

    /**
     * 1️⃣ Montos totales asegurados por tipo de seguro
     */
    public static void mostrarMontosTotales(EntityManager em) {
        Double totalDestruccion = em.createQuery(
                "SELECT SUM(o.montoAsegurado) FROM ObraArtistica o WHERE o.seguroTotal = true",
                Double.class
        ).getSingleResult();

        Double totalParcial = em.createQuery(
                "SELECT SUM(o.montoAsegurado) FROM ObraArtistica o WHERE o.seguroTotal = false",
                Double.class
        ).getSingleResult();

        Double totalGeneral = em.createQuery(
                "SELECT SUM(o.montoAsegurado) FROM ObraArtistica o",
                Double.class
        ).getSingleResult();

        System.out.println("\n===== MONTOS ASEGURADOS (JPQL) =====");
        System.out.printf("Total por destrucción total: $%.2f%n", totalDestruccion);
        System.out.printf("Total por daño parcial:      $%.2f%n", totalParcial);
        System.out.printf("Total general asegurado:     $%.2f%n", totalGeneral);
    }

    /**
     * 2️⃣ Cantidad de obras por estilo artístico
     */
    public static void mostrarObrasPorEstilo(EntityManager em) {
        List<Object[]> resultados = em.createQuery(
                "SELECT o.estilo.nombre, COUNT(o) " +
                "FROM ObraArtistica o GROUP BY o.estilo.nombre",
                Object[].class
        ).getResultList();

        System.out.println("\n===== CANTIDAD DE OBRAS POR ESTILO (JPQL) =====");
        resultados.forEach(r ->
                System.out.printf("%-25s → %3d obras%n", r[0], ((Long) r[1]))
        );
    }

    /**
     * 3️⃣ Obras con daño parcial y monto superior al promedio
     */
    public static void mostrarObrasDanioParcialMayorPromedio(EntityManager em) {
        Double promedio = em.createQuery(
                "SELECT AVG(o.montoAsegurado) FROM ObraArtistica o",
                Double.class
        ).getSingleResult();

        List<ObraArtistica> obras = em.createQuery(
                "SELECT o FROM ObraArtistica o WHERE o.seguroTotal = false AND o.montoAsegurado > :promedio ORDER BY o.anio DESC",
                ObraArtistica.class
        )
        .setParameter("promedio", promedio)
        .getResultList();

        System.out.printf("%n===== Obras con daño parcial y monto > promedio (%.2f) =====%n", promedio);
        obras.forEach(o -> System.out.printf("%s (%d) - $%.2f%n", o.getNombre(), o.getAnio(), o.getMontoAsegurado()));
    }

    /**
     * 4️⃣ Obras de un museo específico
     */
    public static void mostrarObrasDeMuseo(EntityManager em, String museo) {
        TypedQuery<ObraArtistica> query = em.createQuery(
                "SELECT o FROM ObraArtistica o WHERE o.museo.nombre = :museo",
                ObraArtistica.class
        );
        query.setParameter("museo", museo);

        List<ObraArtistica> obras = query.getResultList();

        System.out.printf("%n===== Obras del museo '%s' (JPQL) =====%n", museo);
        obras.forEach(o ->
                System.out.printf("%s (%d) - Autor: %s - Estilo: %s%n",
                        o.getNombre(), o.getAnio(),
                        o.getAutor().getNombre(),
                        o.getEstilo().getNombre())
        );
    }
}