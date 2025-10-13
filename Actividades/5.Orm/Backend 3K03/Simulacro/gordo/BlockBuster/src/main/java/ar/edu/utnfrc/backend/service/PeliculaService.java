package ar.edu.utnfrc.backend.service;

import ar.edu.utnfrc.backend.model.Pelicula;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class PeliculaService {

    private final List<Pelicula> peliculas = new ArrayList<>();

    // 1️⃣ Cargar desde CSV
    public void cargarPeliculasDesdeCSV(String rutaArchivo) throws IOException {
        peliculas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            br.readLine(); // saltea encabezado
            while ((linea = br.readLine()) != null && !linea.trim().isEmpty()) {
                String[] campos = linea.split(";");
                if (campos.length >= 6) {
                    String titulo = campos[0].trim();
                    LocalDate fechaEstreno = LocalDate.parse(campos[1].trim());
                    double precio = Double.parseDouble(campos[2].trim());
                    String clasificacion = campos[3].trim(); // no se usa, pero lo leemos
                    String genero = campos[4].trim();
                    String director = campos[5].trim();
                    peliculas.add(new Pelicula(titulo, director, genero, precio, fechaEstreno));
                }
            }
        }
    }

    // 2️⃣ Listar películas por director
    public List<Pelicula> listarPorDirector(String director) {
        return peliculas.stream()
                .filter(p -> p.getDirector().equalsIgnoreCase(director))
                .collect(Collectors.toList());
    }

    // 3️⃣ Cantidad de películas recientes (≤ 365 días)
    public long contarPeliculasRecientes() {
        LocalDate hoy = LocalDate.now();
        return peliculas.stream()
                .filter(p -> ChronoUnit.DAYS.between(p.getFechaEstreno(), hoy) <= 365)
                .count();
    }

    // 4️⃣ Promedio de precio por género
    public Map<String, Double> promedioPrecioPorGenero() {
        return peliculas.stream()
                .collect(Collectors.groupingBy(
                        Pelicula::getGenero,
                        Collectors.averagingDouble(Pelicula::getPrecio)
                ));
    }

    // 5️⃣ Película más reciente
    public Pelicula obtenerMasReciente() {
        return peliculas.stream()
                .max(Comparator.comparing(Pelicula::getFechaEstreno))
                .orElse(null);
    }
}
