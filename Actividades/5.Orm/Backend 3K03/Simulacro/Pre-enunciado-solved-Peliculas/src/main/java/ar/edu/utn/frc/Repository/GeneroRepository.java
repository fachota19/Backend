package ar.edu.utn.frc.Repository;

import ar.edu.utn.frc.model.Genero;

import java.util.List;
import java.util.stream.Stream;

public class GeneroRepository extends Repository<Genero,Long> {

    public GeneroRepository(){
        super();
    }

    @Override
    public Genero getById(Long id) {
        return this.manager.find(Genero.class,id);
    }

    @Override
    public List<Genero> getAll() {
        return this.manager.createQuery("SELECT g FROM Genero g",Genero.class)
                .getResultList();
    }

    @Override
    public Stream<Genero> getAllStream() {
        return this.manager.createQuery("SELECT g FROM Genero g",Genero.class)
                .getResultStream();
    }
}
