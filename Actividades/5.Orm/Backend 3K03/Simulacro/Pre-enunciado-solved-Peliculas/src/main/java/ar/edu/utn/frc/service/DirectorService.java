package ar.edu.utn.frc.service;

import ar.edu.utn.frc.Repository.DirectorRepository;
import ar.edu.utn.frc.Repository.GeneroRepository;
import ar.edu.utn.frc.model.Director;
import ar.edu.utn.frc.model.Genero;

import java.util.HashMap;

public class DirectorService {
    private HashMap<String, Director> directores;
    private final DirectorRepository directorRepository;

    public DirectorService() {
        this.directorRepository = new DirectorRepository();
        this.directores = new HashMap<>();
    }

    private Director getOrCreate(String nombreDirector){
        if(directores.containsKey(nombreDirector)){
            return directores.get(nombreDirector);
        }
        Director director = new Director(nombreDirector);
        directores.put(nombreDirector, director);
        directorRepository.add(director);
        return director;

    }
}
