package bookstore;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

import bookstore.entities.Libro;

public class MainApp {

    // Configuración de la unidad de persistencia para JPA.
    // Para un proyecto estándar de Maven, esta configuración iría en un archivo
    // 'src/main/resources/META-INF/persistence.xml'
    // Se incluye aquí para que el código sea autocontenido.
    public static EntityManagerFactory getEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("bookstore-unit",
                // Propiedades de la base de datos H2 en modo embebido
                java.util.Map.of(
                        "jakarta.persistence.provider", "org.hibernate.jpa.HibernatePersistenceProvider",
                        "jakarta.persistence.jdbc.driver", "org.h2.Driver",
                        "jakarta.persistence.jdbc.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
                        "jakarta.persistence.jdbc.user", "sa",
                        "jakarta.persistence.jdbc.password", "",
                        "jakarta.persistence.schema-generation.database.action", "drop-and-create",
                        "hibernate.show_sql", "true",
                        "hibernate.format_sql", "true"
                ));
    }

    public static void main(String[] args) {
        // Asume que el archivo 'libros.csv' está en la raíz del proyecto.
        String csvFilePath = "libros.csv";

        // Inicializar el EntityManagerFactory
        EntityManagerFactory emf = getEntityManagerFactory();
        LibroService libroService = new LibroService(emf);

        System.out.println("--- Paso 1: Cargando datos desde el archivo CSV ---");
        libroService.cargarLibrosDesdeCSV(csvFilePath);

        System.out.println("\n--- Paso 2: Listando todos los libros ---");
        List<Libro> libros = libroService.obtenerTodosLosLibros();
        libros.forEach(System.out::println);

        System.out.println("\n--- Paso 3: Actualizando el stock de 'Clean Code' ---");
        String isbnParaActualizar = "978-0134685991";
        libroService.actualizarStockLibro(isbnParaActualizar, 25);
        Libro libroActualizado = libroService.obtenerTodosLosLibros().stream()
                .filter(l -> l.getIsbn().equals(isbnParaActualizar))
                .findFirst().orElse(null);
        System.out.println("Libro después de la actualización: " + libroActualizado);

        System.out.println("\n--- Paso 4: Eliminando el libro 'The Clean Coder' ---");
        String isbnParaEliminar = "978-0132350884";
        libroService.eliminarLibro(isbnParaEliminar);

        System.out.println("\n--- Paso 5: Verificando la lista final de libros ---");
        List<Libro> librosFinales = libroService.obtenerTodosLosLibros();
        librosFinales.forEach(System.out::println);

        // Cerrar el EntityManagerFactory al finalizar
        emf.close();
    }
}
