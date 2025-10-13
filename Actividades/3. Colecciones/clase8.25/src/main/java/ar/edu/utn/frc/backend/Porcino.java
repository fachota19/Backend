package ar.edu.utn.frc.backend;

import java.time.LocalDate;

public final class Porcino extends Animal {

    private final RazaPorcino razaPorcino;

    public Porcino(float aPeso, LocalDate aFechaNacimiento, Sexo aSexo, RazaPorcino aRaza) {
        super(aPeso, aFechaNacimiento, aSexo, aRaza.toString());
        this.razaPorcino = aRaza;
    }

    public RazaPorcino getRazaPorcino() {
        return razaPorcino;
    }

    /**
     * Porcinos: 2 kg alimento por cada kg de peso (por día)
     */
    @Override
    public float getConsumoTotalKg() {
        return redondear(peso * 2f);
    }

    /**
     * Composición Porcinos:
     * 50% maíz, 30% soja, 20% trigo
     */

    @Override
    public Racion[] getDieta() {
        float total = getConsumoTotalKg();
        return new Racion[] {
            racion(Alimento.MAIZ, total, 0.50f),
            racion(Alimento.SOJA, total, 0.30f),
            racion(Alimento.TRIGO, total, 0.20f)
        };
    }
}
