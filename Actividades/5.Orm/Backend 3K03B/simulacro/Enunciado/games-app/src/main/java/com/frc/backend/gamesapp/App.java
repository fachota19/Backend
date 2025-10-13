package com.frc.backend.gamesapp;

import com.frc.backend.gamesapp.modelo.*;
import com.frc.backend.gamesapp.dao.*;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.InputStreamReader;
import java.util.List;

public class App {

    public static void main(String[] args) {
        System.out.println("🚀 Iniciando carga de juegos...");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gamesPU");
        EntityManager em = emf.createEntityManager();

        inicializarBase(em);

        GeneroDAO generoDAO = new GeneroDAO(em);
        PlataformaDAO plataformaDAO = new PlataformaDAO(em);
        DesarrolladorDAO desarrolladorDAO = new DesarrolladorDAO(em);
        JuegoDAO juegoDAO = new JuegoDAO(em);

        int contador = 0;

        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(App.class.getClassLoader().getResourceAsStream("games_data.csv")))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            reader.skip(1); // Saltar encabezado
            String[] linea;

            while ((linea = reader.readNext()) != null) {
                try {
                    if (linea.length < 10) continue; // línea incompleta

                    String titulo = linea[0].trim();
                    String fechaStr = linea[1].trim();
                    String desarrollador = linea[2].trim();
                    String resumen = linea[3].trim();
                    String plataforma = linea[4].trim();
                    String genero = linea[5].trim();
                    String ratingStr = linea[6].trim();
                    String finalizadosStr = linea[7].trim();
                    String jugandoStr = linea[8].trim();
                    String esrb = linea.length > 9 ? linea[9].trim() : "UR"; // Unrated por defecto

                    if (desarrollador.isEmpty() || genero.isEmpty() || plataforma.isEmpty()) continue;

                    // Parsear fecha
                    Integer anio = null;
                    try {
                        if (!fechaStr.isEmpty() && !fechaStr.equalsIgnoreCase("TBD")) {
                            anio = Integer.parseInt(fechaStr.replaceAll("\\D", ""));
                        }
                    } catch (Exception ignored) {}

                    // Parsear rating
                    Double rating = null;
                    if (!ratingStr.equalsIgnoreCase("N/A") && !ratingStr.isEmpty()) {
                        try {
                            rating = Double.parseDouble(ratingStr);
                        } catch (Exception ignored) {}
                    }

                    int finalizados = parsePlays(finalizadosStr);
                    int jugando = parsePlays(jugandoStr);

                    // Persistir entidades relacionadas
                    Genero g = generoDAO.guardarSiNoExiste(genero);
                    Plataforma p = plataformaDAO.guardarSiNoExiste(plataforma);
                    Desarrollador d = desarrolladorDAO.guardarSiNoExiste(desarrollador);

                    // Crear juego
                    ClasificacionESRB clasificacion;
                    try {
                        clasificacion = esrb.isEmpty() ? ClasificacionESRB.UR : ClasificacionESRB.valueOf(esrb.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        clasificacion = ClasificacionESRB.UR; // Valor desconocido → usar "UR"
                    }
                    
                    Juego j = Juego.builder()
                            .titulo(titulo)
                            .fechaLanzamiento(anio)
                            .genero(g)
                            .plataforma(p)
                            .desarrollador(d)
                            .clasificacionESRB(clasificacion)  // ✅ ahora usa el enum correcto
                            .rating(rating)
                            .juegosFinalizados(finalizados)
                            .jugando(jugando)
                            .resumen(resumen)
                            .build();

                    juegoDAO.guardar(j);
                    contador++;

                } catch (Exception e) {
                    System.err.println("⚠️ Línea malformada o error al procesar registro: " + e.getMessage());
                }
            }

            System.out.println("✅ Juegos cargados correctamente: " + contador);
            System.out.println("🎮 Ejemplos de juegos cargados:");
            List<Juego> juegos = juegoDAO.listarTodos();
            juegos.stream().limit(5)
                    .forEach(j -> System.out.println("   🎮 " + j.getTitulo() + " (" + j.getGenero().getNombre() + ")"));

        } catch (Exception e) {
            System.err.println("⚠️ Línea malformada o error al procesar registro: " + e.getMessage());
        }finally {
            em.close();
            emf.close();
            System.out.println("🏁 Finalizado.");
        }
    }

    private static void inicializarBase(EntityManager em) {
        try {
            em.getTransaction().begin();
            em.createNativeQuery("SELECT 1").getSingleResult();
            em.getTransaction().commit();
            System.out.println("✅ Base de datos inicializada correctamente (DDL ejecutado).");
        } catch (Exception e) {
            System.err.println("⚠️ Error inicializando base: " + e.getMessage());
        }
    }

    private static int parsePlays(String valor) {
        if (valor == null || valor.isEmpty()) return 0;
        valor = valor.replace("K", "").trim();
        try {
            double num = Double.parseDouble(valor);
            return valor.contains(".") ? (int) (num * 1000) : (int) num;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}