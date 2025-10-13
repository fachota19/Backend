package com.frc.backend.services;

import com.frc.backend.modelo.BoardGame;

import javax.persistence.EntityManager;
import java.util.List;

public class ReportService {

    // ---------------------------------------------------------------------
    // 1️⃣ Categorías con peor promedio (>500 usuarios)
    // ---------------------------------------------------------------------
    public static void mostrarPeoresCategorias(EntityManager em) {
        System.out.println("\n📊 CATEGORÍAS CON PEOR PROMEDIO (>500 usuarios)");
        System.out.println("---------------------------------------------------");

        try {
            List<Object[]> resultados = em.createQuery(
                    "SELECT c.name, ROUND(AVG(b.averageRating), 2) " +
                            "FROM BoardGame b JOIN b.category c " +
                            "WHERE b.usersRating > 500 " +
                            "GROUP BY c.name " +
                            "ORDER BY AVG(b.averageRating) ASC",
                    Object[].class
            ).getResultList();

            if (resultados.isEmpty()) {
                System.out.println("(Sin resultados disponibles)");
                return;
            }

            System.out.printf("%-25s %s%n", "Categoría", "Promedio");
            System.out.println("---------------------------------------------------");

            resultados.forEach(r ->
                    System.out.printf("%-25s %.2f%n", r[0], r[1])
            );

        } catch (Exception e) {
            System.err.println("[FAIL] Error en mostrarPeoresCategorias: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------------
    // 2️⃣ Juegos aptos para 4 jugadores y edad <= 10
    // ---------------------------------------------------------------------
    public static void mostrarAptosParaCuatro(EntityManager em) {
        System.out.println("\n🎯 JUEGOS APTOS PARA 4 JUGADORES (edad ≤ 10)");
        System.out.println("---------------------------------------------------");

        try {
            List<BoardGame> juegos = em.createQuery(
                    "SELECT b FROM BoardGame b " +
                            "JOIN FETCH b.designer " +
                            "JOIN FETCH b.publisher " +
                            "JOIN FETCH b.category " +
                            "WHERE b.minPlayers <= 4 AND b.maxPlayers >= 4 " +
                            "AND (b.minAge IS NULL OR b.minAge <= 10) " +
                            "ORDER BY b.averageRating DESC",
                    BoardGame.class
            ).getResultList();

            if (juegos.isEmpty()) {
                System.out.println("(Sin resultados disponibles)");
                return;
            }

            int i = 1;
            List<BoardGame> top10 = juegos.stream()
                    .limit(10)
                    .collect(java.util.stream.Collectors.toList());

            for (BoardGame b : top10) {
                System.out.printf(
                        "%2d. %-40s | %-20s | %-15s | %-15s | ⭐ %.2f (%d votos)%n",
                        i++,
                        b.getName(),
                        b.getDesigner().getName(),
                        b.getPublisher().getName(),
                        b.getCategory().getName(),
                        b.getAverageRating() != null ? b.getAverageRating() : 0.0,
                        b.getUsersRating() != null ? b.getUsersRating() : 0
                );
            }


        } catch (Exception e) {
            System.err.println("[FAIL] Error en mostrarAptosParaCuatro: " + e.getMessage());
        }
    }
}