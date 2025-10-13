package ar.edu.utn.frc.service;

import ar.edu.utn.frc.dao.JuegoDao;
import ar.edu.utn.frc.domain.ClasificacionEsrb;
import ar.edu.utn.frc.domain.Juego;

import java.util.List;

public class ConsultasService {
    private final JuegoDao juegoDao = new JuegoDao();

    public List<Juego> porPlataforma(String plataforma) {
        return juegoDao.findByPlataformaNombre(plataforma);
    }

    public List<Juego> porGenero(String genero) {
        return juegoDao.findByGeneroNombre(genero);
    }

    public List<Juego> porDesarrollador(String dev) {
        return juegoDao.findByDesarrolladorNombre(dev);
    }

    public List<Juego> porClasificacion(ClasificacionEsrb esrb) {
        return juegoDao.findByClasificacion(esrb);
    }

    public List<Juego> porPlataformaYEsrbPaginado(String plataforma, ClasificacionEsrb esrb, int page, int size) {
        return juegoDao.findByPlataformaYEsrb(plataforma, esrb, page, size);
    }
}