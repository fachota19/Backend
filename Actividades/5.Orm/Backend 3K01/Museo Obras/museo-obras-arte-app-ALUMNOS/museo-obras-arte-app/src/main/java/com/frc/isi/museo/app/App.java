package com.frc.isi.museo.app;

import com.frc.isi.museo.dao.*;
import com.frc.isi.museo.entidades.*;
import jakarta.persistence.*;
import java.util.List;

public class App {

    public static void main(String[] args) {
        System.out.println("=== MUSEO DE OBRAS DE ARTE ===");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("museo");
        EntityManager em = emf.createEntityManager();

        // Paso 1: leer obras desde CSV
        List<ObraArtistica> obras = Acciones.cargarObrasDesdeCSV("src/main/resources/files/obras.csv");

        // Paso 2: crear DAOs
        ObraArtisticaDAO obraDAO = new ObraArtisticaDAO(em);
        AutorDAO autorDAO = new AutorDAO(em);
        MuseoDAO museoDAO = new MuseoDAO(em);
        EstiloArtisticoDAO estiloDAO = new EstiloArtisticoDAO(em);

        // Paso 3: persistir entidades
        for (ObraArtistica obra : obras) {
            if (autorDAO.buscarPorNombre(obra.getAutor().getNombre()) == null)
                autorDAO.guardar(obra.getAutor());

            if (museoDAO.buscarPorNombre(obra.getMuseo().getNombre()) == null)
                museoDAO.guardar(obra.getMuseo());

            if (estiloDAO.buscarPorNombre(obra.getEstilo().getNombre()) == null)
                estiloDAO.guardar(obra.getEstilo());

            obraDAO.guardar(obra);
        }

        System.out.println("✅ Se guardaron " + obras.size() + " obras en la base de datos.");

        // ============================================
        //   ANÁLISIS CON STREAMS (Etapa 4)
        // ============================================
        System.out.println("\nProcesando información de las obras...");

        Acciones.calcularMontosAsegurados(obras);
        Acciones.generarReportePorEstilo(obras);
        Acciones.mostrarObrasConDanioParcialMayorPromedio(obras);
        Acciones.mostrarObrasDeMuseo(obras, "Museo Nacional de Bellas Artes");

        // ============================================
        //   CONSULTAS DIRECTAS A LA BASE (Etapa 5 - JPQL)
        // ============================================
        System.out.println("\n\n===== CONSULTAS JPQL =====");
        ConsultasJPQL.mostrarMontosTotales(em);
        ConsultasJPQL.mostrarObrasPorEstilo(em);
        ConsultasJPQL.mostrarObrasDanioParcialMayorPromedio(em);
        ConsultasJPQL.mostrarObrasDeMuseo(em, "Museo Nacional de Bellas Artes");

        em.close();
        emf.close();
    }
}