package ar.edu.utnfrc.backend;

import ar.edu.utnfrc.backend.menu.Menu;
import ar.edu.utnfrc.backend.model.Pelicula;
import ar.edu.utnfrc.backend.repository.DbContext;
import ar.edu.utnfrc.backend.repository.DbInit;
import ar.edu.utnfrc.backend.service.DirectorService;
import ar.edu.utnfrc.backend.service.GeneroService;
import ar.edu.utnfrc.backend.service.PeliculaService;
import jakarta.persistence.EntityManager;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Inicializar EntityManager a partir del DbContext Singleton
        EntityManager em = DbContext.getInstance().getManager();

        // Cargar películas automáticamente al iniciar
        DbInit init = new DbInit(em);
        init.inicializarBase();

        // Crear servicios
        GeneroService generoService = new GeneroService();
        DirectorService directorService = new DirectorService();
        PeliculaService peliculaService = new PeliculaService(generoService, directorService);

        // Un solo Scanner para toda la app
        final Scanner sc = new Scanner(System.in);
        final DecimalFormat df2 = new DecimalFormat("#0.00");

        // Crear menú principal
        Menu menu = new Menu("🎬 Menú de Opciones de Películas");

        // 1) Cargar CSV manualmente (opcional)
        menu.addOpcion(1, "Cargar Películas desde CSV", () -> {
            try {
                File archivo = new File("src/main/resources/peliculas.csv");
                System.out.println("Leyendo: " + archivo.getAbsolutePath());
                peliculaService.bulkInsert(archivo);
                System.out.println("✅ Películas cargadas correctamente.");
            } catch (Exception e) {
                System.err.println("❌ Error al cargar las películas: " + e.getMessage());
            }
        });

        // 2) Listar películas por director
        menu.addOpcion(2, "Listar películas por director", () -> {
            System.out.print("Director (vacío para todos): ");
            String nombre = sc.nextLine().trim();

            Map<String, List<Pelicula>> mapa = peliculaService.peliculasPorDirectorStream();

            if (!nombre.isEmpty()) {
                boolean found = false;
                for (String key : mapa.keySet()) {
                    if (key.equalsIgnoreCase(nombre)) {
                        imprimirGrupoDirector(key, mapa.get(key));
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No se encontraron películas para el director: " + nombre);
                }
            } else {
                if (mapa.isEmpty()) {
                    System.out.println("No hay películas cargadas.");
                } else {
                    for (Map.Entry<String, List<Pelicula>> e : mapa.entrySet()) {
                        imprimirGrupoDirector(e.getKey(), e.getValue());
                    }
                }
            }
        });

        // 3) Cantidad de películas recientes (<= 365 días)
        menu.addOpcion(3, "Cantidad de películas recientes (<= 365 días)", () -> {
            long cant = peliculaService.cantidadPeliculasRecientesStream();
            System.out.println("Películas recientes: " + cant);
        });

        // 4) Promedio de precio por género
        menu.addOpcion(4, "Promedio de precio por género", () -> {
            Map<String, Double> promedios = peliculaService.promPrecioPorGeneroStream();
            if (promedios.isEmpty()) {
                System.out.println("No hay películas cargadas.");
            } else {
                System.out.println("\n--- Promedio de precio base por género ---");
                promedios.forEach((genero, promedio) ->
                        System.out.printf("%-15s → $%.2f%n", genero, promedio)
                );
            }
        });

        // 5) Película más reciente
        menu.addOpcion(5, "Mostrar película más reciente", () -> {
            Pelicula p = peliculaService.masRecienteStream();
            if (p == null) {
                System.out.println("No hay películas cargadas.");
            } else {
                System.out.println("Más reciente:");
                System.out.println(" - Título: " + p.getTitulo());
                System.out.println(" - Fecha estreno: " + p.getFechaEstreno());
                System.out.println(" - Director: " + (p.getDirector() != null ? p.getDirector().getNombre() : "-"));
                System.out.println(" - Género: " + (p.getGenero() != null ? p.getGenero().getNombre() : "-"));
                System.out.println(" - Precio base: $" + df2.format(p.getPrecioBaseAlquiler()));
            }
        });

        // Ejecutar menú
        menu.ejecutar();

        // Cerrar scanner
        sc.close();

        // No cerramos EntityManager porque el singleton lo mantiene abierto durante la sesión
        System.out.println("👋 Programa finalizado.");
    }

    private static void imprimirGrupoDirector(String director, List<Pelicula> lista) {
        System.out.println("\nDirector: " + director + " (total: " + lista.size() + ")");
        for (Pelicula p : lista) {
            System.out.println(" - " + p.getTitulo() + " | " + p.getFechaEstreno() +
                    " | $" + p.getPrecioBaseAlquiler() +
                    " | " + (p.getGenero() != null ? p.getGenero().getNombre() : "-"));
        }
    }
}