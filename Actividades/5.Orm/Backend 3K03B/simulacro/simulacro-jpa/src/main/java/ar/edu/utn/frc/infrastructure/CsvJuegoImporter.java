package ar.edu.utn.frc.infrastructure;

import ar.edu.utn.frc.dao.*;
import ar.edu.utn.frc.domain.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CsvJuegoImporter {

    // DAOs (sin Spring)
    private final GeneroDao generoDao = new GeneroDao();
    private final DesarrolladorDao desarrolladorDao = new DesarrolladorDao();
    private final PlataformaDao plataformaDao = new PlataformaDao();
    private final JuegoDao juegoDao = new JuegoDao();

    /**
     * Lee un CSV desde resources (ruta relativa dentro de classpath) y hace upsert básico.
     * Delimitador: ';'
     * Cabeceras: TITULO;ANIO;GENERO;DESARROLLADOR;PLATAFORMA;ESRB;RATING;FINALIZADOS;JUGANDO;RESUMEN
     */
    public void importFromResource(String resourcePath) {
        try (var in = getClass().getClassLoader().getResourceAsStream(resourcePath);
             var br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {

            String header = br.readLine(); // consume cabecera
            if (header == null) throw new IllegalStateException("CSV vacío: " + resourcePath);

            String line;
            int lineNo = 1;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.isBlank()) continue;
                String[] cols = line.split(";", -1);
                if (cols.length < 10) {
                    throw new IllegalArgumentException("Fila " + lineNo + " inválida. Se esperaban 10 columnas.");
                }

                String titulo        = cols[0].trim();
                String anioStr       = cols[1].trim();
                String generoNombre  = cols[2].trim();
                String desaNombre    = cols[3].trim();
                String platNombre    = cols[4].trim();
                String esrbCode      = cols[5].trim();    // E, E10+, T, M, AO, RP
                String ratingStr     = cols[6].trim();
                String finalizadosStr= cols[7].trim();
                String jugandoStr    = cols[8].trim();
                String resumen       = cols[9].trim();

                if (titulo.isEmpty() || generoNombre.isEmpty() || desaNombre.isEmpty()
                        || platNombre.isEmpty() || esrbCode.isEmpty() || resumen.isEmpty()) {
                    throw new IllegalArgumentException("Fila " + lineNo + ": campos obligatorios vacíos.");
                }

                Integer anio         = safeInt(anioStr);
                Double  rating       = safeDouble(ratingStr);
                Integer finalizados  = safeInt(finalizadosStr);
                Integer jugando      = safeInt(jugandoStr);
                ClasificacionEsrb esrb = ClasificacionEsrb.fromCode(esrbCode); // valida ESRB

                // upsert de catálogos por nombre (case-insensitive)
                Genero genero = generoDao.findByNombreIgnoreCase(generoNombre)
                        .orElseGet(() -> generoDao.save( new Genero(){{
                            setNombre(generoNombre);
                        }}));

                Desarrollador desa = desarrolladorDao.findByNombreIgnoreCase(desaNombre)
                        .orElseGet(() -> desarrolladorDao.save( new Desarrollador(){{
                            setNombre(desaNombre);
                        }}));

                Plataforma plat = plataformaDao.findByNombreIgnoreCase(platNombre)
                        .orElseGet(() -> plataformaDao.save( new Plataforma(){{
                            setNombre(platNombre);
                        }}));

                // “upsert” simplificado por título + plataforma (criterio simple y práctico)
                var existentes = juegoDao.findByTituloContainsIgnoreCase(titulo).stream()
                        .filter(j -> j.getPlataforma() != null
                                && j.getPlataforma().getNombre().equalsIgnoreCase(platNombre)
                                && j.getTitulo().equalsIgnoreCase(titulo))
                        .toList();

                if (existentes.isEmpty()) {
                    var j = new Juego();
                    j.setTitulo(titulo);
                    j.setFechaLanzamiento(anio);
                    j.setClasificacion(esrb);
                    j.setGenero(genero);
                    j.setDesarrollador(desa);
                    j.setPlataforma(plat);
                    j.setRating(rating);
                    j.setJuegosFinalizados(finalizados);
                    j.setJugando(jugando);
                    j.setResumen(resumen);
                    juegoDao.save(j);
                } else {
                    var j = existentes.get(0);
                    j.setFechaLanzamiento(anio);
                    j.setClasificacion(esrb);
                    j.setGenero(genero);
                    j.setDesarrollador(desa);
                    j.setPlataforma(plat);
                    j.setRating(rating);
                    j.setJuegosFinalizados(finalizados);
                    j.setJugando(jugando);
                    j.setResumen(resumen);
                    juegoDao.update(j);
                }
            }

            System.out.println("[CSV] Import OK: " + resourcePath);
        } catch (Exception e) {
            throw new RuntimeException("Error importando CSV: " + resourcePath, e);
        }
    }

    private static Integer safeInt(String s) {
        if (s == null || s.isBlank()) return null;
        return Integer.valueOf(s);
    }
    private static Double safeDouble(String s) {
        if (s == null || s.isBlank()) return null;
        return Double.valueOf(s);
    }
}