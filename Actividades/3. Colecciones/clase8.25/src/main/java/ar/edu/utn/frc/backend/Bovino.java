package ar.edu.utn.frc.backend;

import java.time.LocalDate;

public final class Bovino extends Animal {

    private final RazaBovino razaBovino;

    public Bovino(float aPeso, LocalDate aFechaNacimiento, Sexo aSexo, RazaBovino aRaza) {
        super(aPeso, aFechaNacimiento, aSexo, aRaza.toString());
        this.razaBovino = aRaza;
    }

    public RazaBovino getRazaBovino() {
        return razaBovino;
    }

    /**
     * Bovinos: 1.5 kg alimento por cada kg de peso (por día)
     */
    @Override
    public float getConsumoTotalKg() {
        return redondear(peso * 1.5f);
    }

    /**
     * Composición Bovinos:
     * 40% maíz, 30% soja, 30% forraje
     */
    @Override
    public Racion[] getDieta() {
        float total = getConsumoTotalKg();
        return new Racion[] {
            racion(Alimento.MAIZ, total, 0.40f),
            racion(Alimento.SOJA, total, 0.30f),
            racion(Alimento.FORRAJE, total, 0.30f)
        };
    }
}
