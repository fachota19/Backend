package ar.edu.utn.frc;

import ar.edu.utn.frc.dao.*;
import ar.edu.utn.frc.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class JuegoDaoTest extends BaseDaoTest {

    @Test
    void crudYConsultasBasicas() {
        var generoDao = new GeneroDao();
        var devDao    = new DesarrolladorDao();
        var platDao   = new PlataformaDao();
        var juegoDao  = new JuegoDao();

        // seed catálogos
        var acc = new Genero(); acc.setNombre("Acción"); generoDao.save(acc);
        var rpg = new Genero(); rpg.setNombre("RPG");    generoDao.save(rpg);
        var from = new Desarrollador(); from.setNombre("FromSoftware"); devDao.save(from);
        var ps5  = new Plataforma(); ps5.setNombre("PlayStation 5");    platDao.save(ps5);

        // create
        var j = new Juego();
        j.setTitulo("Elden Ring");
        j.setFechaLanzamiento(2022);
        j.setClasificacion(ClasificacionEsrb.M);
        j.setGenero(rpg);
        j.setDesarrollador(from);
        j.setPlataforma(ps5);
        j.setRating(9.5);
        j.setJuegosFinalizados(1_000_000);
        j.setJugando(120_000);
        j.setResumen("Acción-RPG en mundo abierto");
        j = juegoDao.save(j);

        assertNotNull(j.getId());

        // read
        var byId = juegoDao.findById(j.getId()).orElseThrow();
        assertEquals("Elden Ring", byId.getTitulo());

        // update
        byId.setRating(9.6);
        var updated = juegoDao.update(byId);
        assertEquals(9.6, updated.getRating(), 0.0001);

        // query por plataforma
        List<Juego> ps5Games = juegoDao.findByPlataformaNombre("playstation 5");
        assertFalse(ps5Games.isEmpty());

        // delete
        boolean deleted = juegoDao.deleteById(j.getId());
        assertTrue(deleted);
        assertTrue(juegoDao.findById(j.getId()).isEmpty());
    }
}