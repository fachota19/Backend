package ar.edu.utn.frc.backend;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class ComposicionDieta {
    private final String nombre;
    private final String descripcion;
    private final Map<Alimento, Integer> composicion; // % por alimento (debe sumar 100)

    public ComposicionDieta(String nombre, String descripcion, Map<Alimento, Integer> composicion) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("nombre requerido");
        if (descripcion == null || descripcion.isBlank()) throw new IllegalArgumentException("descripcion requerida");
        if (composicion == null || composicion.isEmpty()) throw new IllegalArgumentException("composicion requerida");

        int suma = 0;
        Map<Alimento, Integer> tmp = new EnumMap<>(Alimento.class);
        for (Map.Entry<Alimento, Integer> e : composicion.entrySet()) {
            if (e.getKey() == null) throw new IllegalArgumentException("alimento null");
            int p = e.getValue() == null ? 0 : e.getValue();
            if (p < 0 || p > 100) throw new IllegalArgumentException("porcentaje invalido para " + e.getKey());
            if (tmp.put(e.getKey(), p) != null) throw new IllegalArgumentException("alimento repetido: " + e.getKey());
            suma += p;
        }
        if (suma != 100) throw new IllegalArgumentException("la suma debe ser 100 (actual=" + suma + ")");

        this.nombre = nombre.trim();
        this.descripcion = descripcion.trim();
        this.composicion = Collections.unmodifiableMap(tmp);
    }

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Map<Alimento, Integer> getComposicion() { return composicion; }
}

