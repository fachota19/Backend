package bookstore;

import jakarta.persistence.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import bookstore.entities.Autor;
import bookstore.entities.Editorial;
import bookstore.entities.Libro;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class LibroService {

    private final EntityManagerFactory emf;

    public LibroService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Lee un archivo CSV y carga los libros en la base de datos, manejando la persistencia de
     * Autor y Editorial para evitar duplicados.
     *
     * @param filePath La ruta del archivo CSV.
     */
    public void cargarLibrosDesdeCSV(String filePath) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
    try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            tx.begin();
            String[] header = reader.readNext(); // Leer la cabecera
            String[] record;
            while ((record = reader.readNext()) != null) {
                String isbn = record[0].trim();
                String titulo = record[1].trim();
                String autorNombre = record[2].trim();
                String autorApellido = record[3].trim();
                String editorialNombre = record[4].trim();
                int stock = Integer.parseInt(record[5].trim());
                double precio = Double.parseDouble(record[6].trim());

                // Buscar o crear autor
                TypedQuery<Autor> autorQuery = em.createQuery(
                        "SELECT a FROM Autor a WHERE a.nombre = :nombre AND a.apellido = :apellido", Autor.class);
                autorQuery.setParameter("nombre", autorNombre);
                autorQuery.setParameter("apellido", autorApellido);
                Autor autor = autorQuery.getResultStream().findFirst().orElseGet(() -> {
                    Autor nuevoAutor = new Autor(autorNombre, autorApellido);
                    em.persist(nuevoAutor);
                    return nuevoAutor;
                });

                // Buscar o crear editorial
                TypedQuery<Editorial> editorialQuery = em.createQuery(
                        "SELECT e FROM Editorial e WHERE e.nombre = :nombre", Editorial.class);
                editorialQuery.setParameter("nombre", editorialNombre);
                Editorial editorial = editorialQuery.getResultStream().findFirst().orElseGet(() -> {
                    Editorial nuevaEditorial = new Editorial(editorialNombre);
                    em.persist(nuevaEditorial);
                    return nuevaEditorial;
                });

                // Crear y persistir el libro
                Libro libro = new Libro(isbn, titulo, stock, precio, autor, editorial);
                em.persist(libro);
                System.out.println("Libro cargado: " + libro.getTitulo());
            }
            tx.commit();
    } catch (IOException | CsvValidationException | NumberFormatException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al cargar datos desde el CSV: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene una lista de todos los libros en la base de datos.
     *
     * @return Lista de libros.
     */
    public List<Libro> obtenerTodosLosLibros() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Libro l", Libro.class).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza el stock de un libro.
     *
     * @param isbn El ISBN del libro a actualizar.
     * @param nuevoStock La nueva cantidad de stock.
     * @return true si el libro se actualizó, false en caso contrario.
     */
    public boolean actualizarStockLibro(String isbn, int nuevoStock) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Libro libro = em.find(Libro.class, isbn);
            if (libro != null) {
                libro.setStock(nuevoStock);
                em.merge(libro);
                tx.commit();
                System.out.println("Stock actualizado para el libro: " + libro.getTitulo());
                return true;
            }
            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al actualizar el stock: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un libro de la base de datos.
     *
     * @param isbn El ISBN del libro a eliminar.
     * @return true si el libro se eliminó, false en caso contrario.
     */
    public boolean eliminarLibro(String isbn) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Libro libro = em.find(Libro.class, isbn);
            if (libro != null) {
                em.remove(libro);
                tx.commit();
                System.out.println("Libro eliminado: " + libro.getTitulo());
                return true;
            }
            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al eliminar el libro: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }
}
