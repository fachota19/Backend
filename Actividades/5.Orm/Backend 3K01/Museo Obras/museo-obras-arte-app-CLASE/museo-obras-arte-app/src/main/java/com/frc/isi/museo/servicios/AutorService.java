package com.frc.isi.museo.servicios;

import java.util.HashMap;
import java.util.Map;

import com.frc.isi.museo.entidades.Autor;
import com.frc.isi.museo.repositorios.AutorRepository;

public class AutorService {
    private final AutorRepository autorRepository;
    private final Map<String, Autor> autores;

    public AutorService() {
        autorRepository = new AutorRepository();
        autores = new HashMap<>();
    }

    public Autor getOrCreateAutor(String nombre) {
        return this.autores.computeIfAbsent(nombre, nom -> {
            Autor autor = new Autor();
            autor.setNombre(nom);
            autorRepository.add(autor);
            return autor;
        });
    }
}
