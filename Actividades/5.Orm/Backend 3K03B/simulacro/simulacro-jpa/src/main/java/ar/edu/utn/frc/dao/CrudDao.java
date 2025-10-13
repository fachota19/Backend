package ar.edu.utn.frc.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T, ID> {
    T save(T entity);                 // insert
    T update(T entity);               // update
    boolean deleteById(ID id);        // delete
    Optional<T> findById(ID id);      // select by id
    List<T> findAll();                // select *
    List<T> findAll(int page, int size); // paginado básico
}