package ar.edu.utnfrc.backend.repository;

import ar.edu.utnfrc.backend.model.Clasificacion;
import ar.edu.utnfrc.backend.model.Director;
import ar.edu.utnfrc.backend.model.Genero;
import ar.edu.utnfrc.backend.model.Pelicula;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class DbInit {

    private final EntityManager em;

    public DbInit(EntityManager em) {
        this.em = em;
    }

    public void inicializarBase() {
        System.out.println("🎬 Iniciando carga de películas desde CSV...");

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/peliculas.csv"))) {
            String linea;
            boolean primera = true;

            while ((linea = br.readLine()) != null) {
                if (primera) {
                    primera = false; // Saltar encabezado
                    continue;
                }

                String[] datos = linea.split(";");
                if (datos.length < 6) continue;

                String titulo = datos[0].trim();
                LocalDate fechaEstreno = LocalDate.parse(datos[1].trim());
                double precio = Double.parseDouble(datos[2].trim());
                Clasificacion clasificacion = Clasificacion.valueOf(datos[3].trim());
                String nombreGenero = datos[4].trim();
                String nombreDirector = datos[5].trim();

                // Buscar o crear género
                Genero genero = em.createQuery("SELECT g FROM Genero g WHERE g.nombre = :nombre", Genero.class)
                        .setParameter("nombre", nombreGenero)
                        .getResultStream()
                        .findFirst()
                        .orElseGet(() -> {
                            Genero nuevo = new Genero();
                            nuevo.setNombre(nombreGenero);
                            em.persist(nuevo);
                            return nuevo;
                        });

                // Buscar o crear director
                Director director = em.createQuery("SELECT d FROM Director d WHERE d.nombre = :nombre", Director.class)
                        .setParameter("nombre", nombreDirector)
                        .getResultStream()
                        .findFirst()
                        .orElseGet(() -> {
                            Director nuevo = new Director();
                            nuevo.setNombre(nombreDirector);
                            em.persist(nuevo);
                            return nuevo;
                        });

                // Crear y persistir película
                Pelicula pelicula = new Pelicula();
                pelicula.setTitulo(titulo);
                pelicula.setFechaEstreno(fechaEstreno);
                pelicula.setPrecioBaseAlquiler(precio);
                pelicula.setClasificacion(clasificacion);
                pelicula.setGenero(genero);
                pelicula.setDirector(director);

                em.persist(pelicula);
            }

            tx.commit();
            System.out.println("✅ Películas cargadas correctamente desde el CSV.");

        } catch (IOException e) {
            tx.rollback();
            System.err.println("❌ Error leyendo el archivo CSV: " + e.getMessage());
        } catch (Exception e) {
            tx.rollback();
            System.err.println("❌ Error al cargar películas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}