package ar.edu.utn.frc.backend;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.junit.jupiter.api.Test;

import ar.edu.utn.frc.backend.modelo.Auto;

public class CollectionTest {

    private List<Auto> cargarListaDeAuto() throws FileNotFoundException {
    List<Auto> autos = new LinkedList<>();

    String ruta = getClass().getClassLoader().getResource("autos.csv").getFile();
    ruta = URLDecoder.decode(ruta, StandardCharsets.UTF_8);

    try (Scanner sc = new Scanner(new File(ruta), StandardCharsets.UTF_8.name())) {
        while (sc.hasNextLine()) {
            String linea = sc.nextLine().trim();
            if (linea.isEmpty()) continue;

            String[] parts = linea.split(",", -1);
            if (parts.length < 4) continue;

            int anio = Integer.parseInt(parts[0].trim());
            String marca = parts[1].trim();
            String modelo = parts[2].trim();
            String tipo = parts[3].trim();

            autos.add(new ar.edu.utn.frc.backend.modelo.Auto(
                anio, marca, modelo, List.of(tipo)
            ));
        }
    }
    return autos;
	}

    @Test
    void testObtenerCantidadPorMarcaYAnio_Ferrari2019() throws Exception {
        List<Auto> autos = cargarListaDeAuto();

        // Conteo manual (independiente) para validar el método
        int esperado = 0;
        for (Auto a : autos) {
            if (a.getAnio() == 2019 && "Ferrari".equalsIgnoreCase(a.getMarca())) {
                esperado++;
            }
        }

        int obtenido = Collection.obtenerCantidadPorMarcaYAnio(autos, "Ferrari", 2019);
        assertEquals(esperado, obtenido);
        assertTrue(obtenido > 0, "Debería haber al menos un Ferrari 2019 según el CSV");
    }

    @Test
    void testObtenerCantidadDeAutosPorMarca_Propiedades() throws Exception {
        List<Auto> autos = cargarListaDeAuto();

        Map<String, Integer> porMarca = Collection.obtenerCantidadDeAutosPorMarca(autos);

        // 1) La suma de los valores debe ser el total de autos
        int suma = porMarca.values().stream().mapToInt(Integer::intValue).sum();
        assertEquals(autos.size(), suma, "La suma de cantidades por marca debe igualar el total de autos");

        // 2) Las claves deben coincidir con el conjunto de marcas existentes
        Set<String> marcasUnicas = new HashSet<>();
        for (Auto a : autos) marcasUnicas.add(a.getMarca());
        assertEquals(marcasUnicas, porMarca.keySet(), "Las claves del map deben ser las marcas únicas");

        // 3) Checks suaves sobre marcas que sabemos que existen en el CSV
        assertTrue(porMarca.getOrDefault("Ferrari", 0) > 0, "Debe haber Ferrari en el CSV");
        assertTrue(porMarca.getOrDefault("FIAT", 0) > 0, "Debe haber FIAT en el CSV");
    }

    @Test
    void testObtenerCantidadDeAutosPorAnio_PropiedadesY2019() throws Exception {
        List<Auto> autos = cargarListaDeAuto();

        Map<Integer, Integer> porAnio = Collection.obtenerCantidadDeAutosPorAnio(autos);

        // 1) La suma de los valores debe ser el total de autos
        int suma = porAnio.values().stream().mapToInt(Integer::intValue).sum();
        assertEquals(autos.size(), suma, "La suma de cantidades por año debe igualar el total de autos");

        // 2) Las claves deben coincidir con el conjunto de años existentes
        Set<Integer> aniosUnicos = new HashSet<>();
        for (Auto a : autos) aniosUnicos.add(a.getAnio());
        assertEquals(aniosUnicos, porAnio.keySet(), "Las claves del map deben ser los años únicos");

        // 3) Check puntual que ya estaba en tu boceto de test
        assertEquals(20, porAnio.get(2019));
    }
}