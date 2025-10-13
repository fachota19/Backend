package ar.edu.utn.frc;

import ar.edu.utn.frc.dao.*;
import ar.edu.utn.frc.domain.*;
import ar.edu.utn.frc.infrastructure.JpaUtil;

public class App {
    public static void main(String[] args) {
        // 1) Crear esquema desde ddl.sql (antes de inicializar JPA/DAOs)
        SchemaInitializer.runDdl();

        try {
            // 2) DAOs (sin Spring)
            var generoDao = new GeneroDao();
            var devDao    = new DesarrolladorDao();
            var platDao   = new PlataformaDao();
            var juegoDao  = new JuegoDao();

            // 3) Insert catálogos (idempotente simple si corrés varias veces)
            var acc = generoDao.findByNombreIgnoreCase("Acción")
                    .orElseGet(() -> { var g = new Genero(); g.setNombre("Acción"); return generoDao.save(g); });
            var rpg = generoDao.findByNombreIgnoreCase("RPG")
                    .orElseGet(() -> { var g = new Genero(); g.setNombre("RPG"); return generoDao.save(g); });

            var from = devDao.findByNombreIgnoreCase("FromSoftware")
                    .orElseGet(() -> { var d = new Desarrollador(); d.setNombre("FromSoftware"); return devDao.save(d); });
            var nd = devDao.findByNombreIgnoreCase("Naughty Dog")
                    .orElseGet(() -> { var d = new Desarrollador(); d.setNombre("Naughty Dog"); return devDao.save(d); });

            var ps5 = platDao.findByNombreIgnoreCase("PlayStation 5")
                    .orElseGet(() -> { var p = new Plataforma(); p.setNombre("PlayStation 5"); return platDao.save(p); });
            var pc = platDao.findByNombreIgnoreCase("PC")
                    .orElseGet(() -> { var p = new Plataforma(); p.setNombre("PC"); return platDao.save(p); });

            // 4) Insert juegos (demo)
            if (juegoDao.findByTituloContainsIgnoreCase("Elden Ring").isEmpty()) {
                var j1 = new Juego();
                j1.setTitulo("Elden Ring");
                j1.setFechaLanzamiento(2022);
                j1.setClasificacion(ClasificacionEsrb.M);
                j1.setGenero(rpg);
                j1.setDesarrollador(from);
                j1.setPlataforma(ps5);
                j1.setRating(9.5);
                j1.setJuegosFinalizados(1_000_000);
                j1.setJugando(120_000);
                j1.setResumen("Acción-RPG en mundo abierto");
                juegoDao.save(j1);
            }

            if (juegoDao.findByTituloContainsIgnoreCase("The Last of Us Part I").isEmpty()) {
                var j2 = new Juego();
                j2.setTitulo("The Last of Us Part I");
                j2.setFechaLanzamiento(2022);
                j2.setClasificacion(ClasificacionEsrb.M);
                j2.setGenero(acc);
                j2.setDesarrollador(nd);
                j2.setPlataforma(ps5);
                j2.setRating(9.3);
                j2.setJuegosFinalizados(2_000_000);
                j2.setJugando(30_000);
                j2.setResumen("Acción-aventura narrativa");
                juegoDao.save(j2);
            }

            // 5) Consultas de ejemplo
            System.out.println("— Juegos en PS5 —");
            juegoDao.findByPlataformaNombre("playstation 5")
                    .forEach(x -> System.out.println(" * " + x.getTitulo()));

            System.out.println("— Juegos ESRB M (primeros 2) —");
            juegoDao.findByClasificacion(ClasificacionEsrb.M).stream()
                    .limit(2)
                    .forEach(x -> System.out.println(" * " + x.getTitulo()));

            System.out.println("— Total de juegos: " + juegoDao.findAll().size());

        } finally {
            // 6) Cerrar EMF global siempre
            JpaUtil.close();
        }
    }
}