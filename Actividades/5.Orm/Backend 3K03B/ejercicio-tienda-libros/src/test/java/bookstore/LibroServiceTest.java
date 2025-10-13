package bookstore;

import bookstore.LibroService;
import bookstore.MainApp;
import bookstore.entities.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibroServiceTest {

    private static EntityManagerFactory emf;
    private LibroService libroService;
    private static final String TEST_CSV_PATH = "test_libros.csv";

    @BeforeAll
    static void setup() {
        // Crear un archivo CSV de prueba para los tests
        try (FileWriter writer = new FileWriter(TEST_CSV_PATH)) {
            writer.write("isbn,titulo,autor_nombre,autor_apellido,editorial_nombre,stock,precio\n");
            writer.write("978-0134685991,Clean Code,Robert,Martin,Prentice Hall,15,59.99\n");
            writer.write("978-0321765723,The Pragmatic Programmer,Andrew,Hunt,Addison-Wesley,10,45.50\n");
            writer.write("978-0132350884,The Clean Coder,Robert,Martin,Prentice Hall,8,55.00\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Inicializar el EntityManagerFactory para las pruebas
        emf = MainApp.getEntityManagerFactory();
    }

    @BeforeEach
    void init() {
        // Crear una nueva instancia del servicio antes de cada test
        libroService = new LibroService(emf);
    }

    @AfterAll
    static void tearDown() {
        // Cerrar el EntityManagerFactory al finalizar todas las pruebas
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    @DisplayName("Verifica la carga de libros desde el CSV")
    void testCargarLibrosDesdeCSV() {
        // Cargar los datos de prueba
        libroService.cargarLibrosDesdeCSV(TEST_CSV_PATH);

        // Verificar que los 3 libros de prueba fueron cargados correctamente
        List<Libro> libros = libroService.obtenerTodosLosLibros();
        assertNotNull(libros, "La lista de libros no debe ser nula");
        assertEquals(3, libros.size(), "Debería haber 3 libros en la base de datos");

        // Verificar que un libro específico se cargó con los datos correctos
        Libro libro = libros.stream()
                .filter(l -> "978-0134685991".equals(l.getIsbn()))
                .findFirst().orElse(null);
        assertNotNull(libro, "El libro 'Clean Code' debería existir");
        assertEquals("Clean Code", libro.getTitulo());
        assertEquals(15, libro.getStock());
    }

    @Test
    @DisplayName("Verifica la actualización del stock de un libro")
    void testActualizarStockLibro() {
        // Cargar datos iniciales
        libroService.cargarLibrosDesdeCSV(TEST_CSV_PATH);

        // Actualizar el stock del libro 'Clean Code' (ISBN: 978-0134685991)
        String isbn = "978-0134685991";
        int nuevoStock = 50;
        assertTrue(libroService.actualizarStockLibro(isbn, nuevoStock), "La actualización debe ser exitosa");

        // Verificar que el stock se ha actualizado en la base de datos
        Libro libroActualizado = libroService.obtenerTodosLosLibros().stream()
                .filter(l -> isbn.equals(l.getIsbn()))
                .findFirst().orElse(null);

        assertNotNull(libroActualizado, "El libro actualizado no debe ser nulo");
        assertEquals(nuevoStock, libroActualizado.getStock(), "El stock del libro debe coincidir con el valor actualizado");
    }

    @Test
    @DisplayName("Verifica la eliminación de un libro")
    void testEliminarLibro() {
        // Cargar datos iniciales
        libroService.cargarLibrosDesdeCSV(TEST_CSV_PATH);

        // Eliminar el libro 'The Pragmatic Programmer' (ISBN: 978-0321765723)
        String isbnParaEliminar = "978-0321765723";
        assertTrue(libroService.eliminarLibro(isbnParaEliminar), "La eliminación debe ser exitosa");

        // Verificar que el libro ya no existe
        List<Libro> librosRestantes = libroService.obtenerTodosLosLibros();
        assertEquals(2, librosRestantes.size(), "Debería haber 2 libros después de la eliminación");
        assertFalse(librosRestantes.stream()
                .anyMatch(l -> isbnParaEliminar.equals(l.getIsbn())), "El libro eliminado no debe estar en la lista");
    }

    @Test
    @DisplayName("Verifica que las entidades Autor y Editorial no se duplican")
    void testNoDuplicarEntidades() {
        // Cargar datos que contienen autores y editoriales repetidos
        libroService.cargarLibrosDesdeCSV(TEST_CSV_PATH);

        EntityManager em = emf.createEntityManager();
        try {
            long totalAutores = em.createQuery("SELECT COUNT(a) FROM Autor a", Long.class).getSingleResult();
            long totalEditoriales = em.createQuery("SELECT COUNT(e) FROM Editorial e", Long.class).getSingleResult();

            // Verificamos que solo haya 2 autores y 2 editoriales (Robert Martin y Andrew Hunt; Prentice Hall y Addison-Wesley)
            assertEquals(2, totalAutores, "No se deben duplicar los autores");
            assertEquals(2, totalEditoriales, "No se deben duplicar las editoriales");
        } finally {
            em.close();
        }
    }
}
