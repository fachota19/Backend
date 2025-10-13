package ar.edu.utn.frc.service;

import ar.edu.utn.frc.Repository.GeneroRepository;
import ar.edu.utn.frc.model.Genero;

import java.util.HashMap;

public class GeneroService {
    private HashMap<String, Genero> generos;
    private final GeneroRepository generoRepository;

    public GeneroService(){
        this.generoRepository = new GeneroRepository();
        this.generos = new HashMap<>();
    }

    private Genero getOrCreate(String nombreGenero){
        if(generos.containsKey(nombreGenero)){
            return generos.get(nombreGenero);
        }
        Genero genero = new Genero(nombreGenero);
        generos.put(nombreGenero, genero);
        generoRepository.add(genero);
        return genero;

    }

}
