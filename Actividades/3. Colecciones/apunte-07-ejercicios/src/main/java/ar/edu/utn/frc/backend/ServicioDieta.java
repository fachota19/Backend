package ar.edu.utn.frc.backend;

import java.util.HashMap;
import java.util.Map;

public class ServicioDieta {
    private final Map<Animal, ComposicionDieta> asignaciones = new HashMap<>();

    /** Requerimiento 3: asigna una plantilla de dieta a un animal */
    public void asignar(Animal animal, ComposicionDieta dieta) {
        if (animal == null) throw new IllegalArgumentException("animal requerido");
        if (dieta == null) throw new IllegalArgumentException("dieta requerida");
        asignaciones.put(animal, dieta);
    }

    /** Obtener la dieta asignada (o null si no tiene) */
    public ComposicionDieta obtener(Animal animal) {
        return asignaciones.get(animal);
    }
}