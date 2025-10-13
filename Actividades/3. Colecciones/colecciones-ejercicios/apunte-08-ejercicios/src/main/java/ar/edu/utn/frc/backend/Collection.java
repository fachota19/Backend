package ar.edu.utn.frc.backend;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.edu.utn.frc.backend.modelo.Auto;

public class Collection {

    /*
     * Devuelve la cantidad de autos de una marca y un año determinado
     *
     * @param autos array de autos
     * @param marca marca a buscar
     * @param anio año a buscar
     * @return cantidad de autos de una marca y un año determinado
     */
    public static int obtenerCantidadPorMarcaYAnio(List<Auto> autos, String marca, int anio) {
        if (autos == null || marca == null) return 0;
        int count = 0;
        for (Auto a : autos) {
            if (a == null) continue;
            if (a.getAnio() == anio && marca.equalsIgnoreCase(a.getMarca())) {
                count++;
            }
        }
        return count;
    }

    /*
     * Devuelve la cantidad de autos convertibles
     *
     * @param autos array de autos
     * @return cantidad de autos convertibles
     */
    public static int obtenerCantidadDeConvertibles(List<Auto> autos) {
        if (autos == null) return 0;
        int count = 0;
        for (Auto a : autos) {
            if (a == null) continue;
            // tipos es List<String>, p.ej: ["Convertible"], ["Hatchback"], etc.
            for (String t : a.getTipos()) {
                if (t != null && t.equalsIgnoreCase("Convertible")) {
                    count++;
                    break; // este auto ya cuenta como convertible
                }
            }
        }
        return count;
    }

    /*
     * Devuelve un set con las marcas que vendan sedanes
     *
     * @param autos array de autos
     * @return set de marcas
     */
    public static Set<String> obtenerLasMarcasQueVendanSedanes(List<Auto> autos) {
        Set<String> marcas = new HashSet<>();
        if (autos == null) return marcas;

        for (Auto a : autos) {
            if (a == null) continue;
            if (tieneTipoSedan(a)) {
                marcas.add(a.getMarca());
            }
        }
        return marcas;
    }

    private static boolean tieneTipoSedan(Auto a) {
        // Aceptamos "Sedan" y "Sedán" (con acento), case-insensitive
        for (String t : a.getTipos()) {
            if (t == null) continue;
            String tl = t.trim().toLowerCase();
            if (tl.equals("sedan") || tl.equals("sedán")) {
                return true;
            }
        }
        return false;
    }

    /*
     * Devuelve un map con la cantidad de autos por marca
     *
     * @param autos array de autos
     * @return map con la cantidad de autos por marca
     */
    public static Map<String, Integer> obtenerCantidadDeAutosPorMarca(List<Auto> autos) {
        Map<String, Integer> conteo = new HashMap<>();
        if (autos == null) return conteo;

        for (Auto a : autos) {
            if (a == null) continue;
            conteo.merge(a.getMarca(), 1, Integer::sum);
        }
        return conteo;
    }

    /*
     * Devuelve un map con la cantidad de autos por año
     *
     * @param autos array de autos
     * @return map con la cantidad de autos por año
     */
    public static Map<Integer, Integer> obtenerCantidadDeAutosPorAnio(List<Auto> autos) {
        Map<Integer, Integer> conteo = new HashMap<>();
        if (autos == null) return conteo;

        for (Auto a : autos) {
            if (a == null) continue;
            conteo.merge(a.getAnio(), 1, Integer::sum);
        }
        return conteo;
    }
}