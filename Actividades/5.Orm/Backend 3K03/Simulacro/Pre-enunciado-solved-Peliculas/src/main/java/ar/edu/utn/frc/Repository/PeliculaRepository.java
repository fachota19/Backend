package ar.edu.utn.frc.Repository;

import ar.edu.utn.frc.model.Pelicula;

import java.util.List;
import java.util.stream.Stream;

public class PeliculaRepository extends Repository<Pelicula,Long> {


    public PeliculaRepository(){
        super();
    }
    @Override
    public Pelicula getById(Long id) {
        return this.manager.find(Pelicula.class,id);
    }


    @Override
    public List<Pelicula> getAll() {
        return this.manager.createQuery("SELECT p from Pelicula p",Pelicula.class)
                .getResultList();
    }

    @Override
    public Stream<Pelicula> getAllStream() {
        return this.manager.createQuery("SELECT p from Pelicula p",Pelicula.class)
                .getResultStream();
    }
}
