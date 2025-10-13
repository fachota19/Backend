package com.frc.isi.museo.app;

import com.frc.isi.museo.entidades.Autor;
import com.frc.isi.museo.entidades.EstiloArtistico;
import com.frc.isi.museo.entidades.Museo;
import com.frc.isi.museo.entidades.ObraArtistica;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * Clase utilitaria para cargar las obras desde el archivo CSV.
 */
public class Acciones {

    public static List<ObraArtistica> cargarObrasDesdeCSV(String rutaCsv) {
        List<ObraArtistica> obras = new ArrayList<>();
        Map<String, Autor> autores = new HashMap<>();
        Map<String, Museo> museos = new HashMap<>();
        Map<String, EstiloArtistico> estilos = new HashMap<>();

        try (Stream<String> lineas = Files.lines(Paths.get(rutaCsv))) {
            lineas.skip(1).forEach(linea -> {
                String[] campos = linea.split(",");

                String nombreObra = campos[0];
                int anio = Integer.parseInt(campos[1]);
                String nombreAutor = campos[2];
                String nombreMuseo = campos[3];
                String nombreEstilo = campos[4];
                double monto = Double.parseDouble(campos[5]);
                boolean total = campos[6].trim().equals("1");

                Autor autor = autores.computeIfAbsent(nombreAutor, k -> new Autor(nombreAutor));
                Museo museo = museos.computeIfAbsent(nombreMuseo, k -> new Museo(nombreMuseo));
                EstiloArtistico estilo = estilos.computeIfAbsent(nombreEstilo, k -> new EstiloArtistico(nombreEstilo));

                obras.add(new ObraArtistica(nombreObra, anio, monto, total, autor, museo, estilo));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return obras;
    }

    // ======================================
    //     MÉTODOS DE ANÁLISIS CON STREAMS
    // ======================================
    
    /**
     * Calcula los montos asegurados según el tipo de seguro.
     */
    public static void calcularMontosAsegurados(List<ObraArtistica> obras) {
        double totalDestruccion = obras.stream()
                .filter(ObraArtistica::getSeguroTotal)
                .mapToDouble(ObraArtistica::getMontoAsegurado)
                .sum();
    
        double totalParcial = obras.stream()
                .filter(o -> !o.getSeguroTotal())
                .mapToDouble(ObraArtistica::getMontoAsegurado)
                .sum();
    
        double totalGeneral = obras.stream()
                .mapToDouble(ObraArtistica::getMontoAsegurado)
                .sum();
    
        System.out.println("\n===== MONTOS ASEGURADOS =====");
        System.out.printf("Total por destrucción total: $%.2f%n", totalDestruccion);
        System.out.printf("Total por daño parcial:      $%.2f%n", totalParcial);
        System.out.printf("Total asegurado general:     $%.2f%n", totalGeneral);
    }
    
    /**
     * Genera un CSV con la cantidad de obras por estilo artístico.
     */
    public static void generarReportePorEstilo(List<ObraArtistica> obras) {
        System.out.println("\nGenerando reporte por estilo artístico...");
    
        Map<String, Long> conteo = obras.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getEstilo().getNombre(),
                        Collectors.counting()
                ));
    
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("reporte_estilos.csv"))) {
            writer.write("Estilo,Cantidad\n");
            for (var entry : conteo.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
            System.out.println("✅ Archivo 'reporte_estilos.csv' generado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Muestra las obras con daño parcial y monto mayor al promedio,
     * ordenadas por año de creación descendente.
     */
    public static void mostrarObrasConDanioParcialMayorPromedio(List<ObraArtistica> obras) {
        double promedio = obras.stream()
                .mapToDouble(ObraArtistica::getMontoAsegurado)
                .average()
                .orElse(0);
    
        System.out.printf("%n===== Obras con daño parcial y monto > promedio (%.2f) =====%n", promedio);
    
        obras.stream()
                .filter(o -> !o.getSeguroTotal() && o.getMontoAsegurado() > promedio)
                .sorted(Comparator.comparing(ObraArtistica::getAnio).reversed())
                .forEach(o -> System.out.printf("%s (%d) - $%.2f%n", o.getNombre(), o.getAnio(), o.getMontoAsegurado()));
    }
    
    /**
     * Muestra las obras de un museo específico.
     */
    public static void mostrarObrasDeMuseo(List<ObraArtistica> obras, String nombreMuseo) {
        System.out.printf("%n===== Obras del museo '%s' =====%n", nombreMuseo);
    
        obras.stream()
                .filter(o -> o.getMuseo().getNombre().equalsIgnoreCase(nombreMuseo))
                .forEach(o -> System.out.printf("%s (%d) - Autor: %s - Estilo: %s%n",
                        o.getNombre(), o.getAnio(),
                        o.getAutor().getNombre(),
                        o.getEstilo().getNombre()));
    }
}