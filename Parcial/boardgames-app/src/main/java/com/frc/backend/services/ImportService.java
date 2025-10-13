package com.frc.backend.services;

import com.frc.backend.modelo.*;
import com.frc.backend.infra.LocalEntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

/**
 * Servicio de importación desde CSV.
 * Carga datos de board_games_data.csv en la base (normalizando entidades relacionadas).
 */
public class ImportService {

    /**
     * Importa el archivo CSV indicado dentro de resources/sql/
     */
    public static void importarDatos(String csvPath) {
        EntityManager em = LocalEntityManagerProvider.getFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try (InputStream is = ImportService.class.getClassLoader().getResourceAsStream(csvPath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            if (is == null) {
                System.err.println("[ERROR] No se encontró el archivo CSV: " + csvPath);
                return;
            }

            tx.begin();

            // Saltar la primera línea (encabezados)
            String linea = br.readLine();

            int count = 0;
            while ((linea = br.readLine()) != null) {

                // Ignorar líneas vacías
                if (linea.trim().isEmpty()) continue;

                // Separar por ';' (según el formato real del CSV)
                String[] campos = linea.split(";", -1);
                if (campos.length < 10) continue;

                // Campos del CSV según el formato:
                // name;category;year_published;designer;min_age;average_rating;users_rated;min_players;max_players;publisher
                String name = campos[0].trim();
                String categoryName = campos[1].trim();
                String yearStr = campos[2].trim();
                String designerName = campos[3].trim();
                String minAgeStr = campos[4].trim();
                String avgStr = campos[5].trim();
                String usersStr = campos[6].trim();
                String minPlayersStr = campos[7].trim();
                String maxPlayersStr = campos[8].trim();
                String publisherName = campos[9].trim();

                // Conversión segura de tipos
                Integer yearPublished = parseInteger(yearStr);
                Integer minAge = parseInteger(minAgeStr);
                BigDecimal averageRating = parseBigDecimal(avgStr);
                Integer usersRating = parseInteger(usersStr);
                Integer minPlayers = parseInteger(minPlayersStr);
                Integer maxPlayers = parseInteger(maxPlayersStr);

                // 🔹 Validaciones de consistencia según el DDL

                // 1. Rating fuera de rango (0..10)
                if (averageRating != null &&
                        (averageRating.compareTo(BigDecimal.ZERO) < 0 ||
                                averageRating.compareTo(BigDecimal.TEN) > 0)) {
                    System.out.printf("⚠️  '%s' omitido: rating fuera de rango (%.2f)%n",
                            name, averageRating);
                    continue;
                }

                // 2. Año inválido (fuera de 1800–2100)
                if (yearPublished != null && (yearPublished < 1800 || yearPublished > 2100)) {
                    System.out.printf("⚠️  '%s' omitido: año inválido (%d)%n",
                            name, yearPublished);
                    continue;
                }

                // 3. Rango de jugadores inválido
                if (minPlayers != null && maxPlayers != null && minPlayers > maxPlayers) {
                    System.out.printf("⚠️  '%s' omitido: rango inválido de jugadores (%d > %d)%n",
                            name, minPlayers, maxPlayers);
                    continue;
                }

                // 🔹 Normalización de entidades relacionadas
                Category category = getOrCreate(em, Category.class, "name", categoryName);
                Designer designer = getOrCreate(em, Designer.class, "name", designerName);
                Publisher publisher = getOrCreate(em, Publisher.class, "name", publisherName);

                // Crear el juego
                BoardGame game = new BoardGame();
                game.setName(name);
                game.setCategory(category);
                game.setDesigner(designer);
                game.setPublisher(publisher);
                game.setYearPublished(yearPublished);
                game.setMinAge(minAge);
                game.setAverageRating(averageRating);
                game.setUsersRating(usersRating);
                game.setMinPlayers(minPlayers);
                game.setMaxPlayers(maxPlayers);

                // Persistir
                em.persist(game);
                count++;
            }

            tx.commit();
            System.out.printf("✅ Importación completada. %d juegos válidos cargados.%n", count);

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("[ERROR] Error al importar CSV: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // ---------------------------------------------------------------------
    // Métodos auxiliares
    // ---------------------------------------------------------------------

    private static Integer parseInteger(String value) {
        try {
            return value.isEmpty() ? null : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static BigDecimal parseBigDecimal(String value) {
        try {
            return value.isEmpty() ? null : new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static <T> T getOrCreate(EntityManager em, Class<T> clazz, String fieldName, String value) {
        if (value == null || value.isEmpty()) return null;

        T entity = em.createQuery(
                "FROM " + clazz.getSimpleName() + " WHERE " + fieldName + " = :value", clazz)
                .setParameter("value", value)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (entity == null) {
            try {
                entity = clazz.getDeclaredConstructor().newInstance();
                clazz.getMethod("setName", String.class).invoke(entity, value);
                em.persist(entity);
            } catch (Exception e) {
                throw new RuntimeException("Error creando entidad " + clazz.getSimpleName(), e);
            }
        }

        return entity;
    }
}