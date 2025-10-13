package ar.edu.utnfrc.backend.menu;

import ar.edu.utnfrc.backend.model.Pelicula;
import ar.edu.utnfrc.backend.service.PeliculaService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final PeliculaService peliculaService = new PeliculaService();

    public void mostrar() {
        int opcion;

        do {
            System.out.println("\n=== MENÚ BLOCKBUSTER ===");
            System.out.println("1. Cargar Películas desde CSV");
            System.out.println("2. Listar Películas por Director");
            System.out.println("3. Cantidad de Películas Recientes (≤ 365 días)");
            System.out.println("4. Promedio de Precio por Género");
            System.out.println("5. Mostrar Película más Reciente");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> cargarPeliculas();
                case 2 -> listarPorDirector();
                case 3 -> contarRecientes();
                case 4 -> promedioPorGenero();
                case 5 -> peliculaMasReciente();
                case 6 -> System.out.println("👋 Fin del programa.");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 6);
    }

   private void cargarPeliculas() {
    try {
        var recurso = getClass().getClassLoader().getResource("peliculas.csv");

        if (recurso == null) {
            System.out.println("⚠️ No se encontró el archivo peliculas.csv en resources.");
            return;
        }

        // 🔥 Convertir de URL a Path real (decodifica espacios y caracteres especiales)
        java.nio.file.Path path = java.nio.file.Paths.get(recurso.toURI());
        String ruta = path.toString();

        peliculaService.cargarPeliculasDesdeCSV(ruta);
        System.out.println("✅ Películas cargadas exitosamente desde: " + ruta);

    } catch (IOException e) {
        System.out.println("⚠️ Error al leer el archivo CSV: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("⚠️ Error inesperado: " + e.getMessage());
    }
}




    private void listarPorDirector() {
        System.out.print("Ingrese el nombre del director: ");
        String nombre = scanner.nextLine();
        List<Pelicula> peliculas = peliculaService.listarPorDirector(nombre);
        if (peliculas.isEmpty()) {
            System.out.println("No se encontraron películas del director: " + nombre);
        } else {
            peliculas.forEach(System.out::println);
        }
    }

    private void contarRecientes() {
        long cantidad = peliculaService.contarPeliculasRecientes();
        System.out.println("Películas estrenadas en los últimos 365 días: " + cantidad);
    }

    private void promedioPorGenero() {
        peliculaService.promedioPrecioPorGenero()
                .forEach((genero, promedio) ->
                        System.out.printf("%s → Promedio de precio: $%.2f%n", genero, promedio));
    }

    private void peliculaMasReciente() {
        Pelicula p = peliculaService.obtenerMasReciente();
        if (p != null)
            System.out.println("🎞️ Película más reciente: " + p);
        else
            System.out.println("No hay películas cargadas.");
    }
}
