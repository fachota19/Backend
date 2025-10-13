package ar.edu.utn.frc.backend;

import java.time.LocalDate;
import java.util.Arrays;

public abstract class Animal {

    protected float peso;                    // en kg
    private final LocalDate fechaNacimiento;
    private final Sexo sexo;
    private final String raza;               // guardo el nombre legible de la raza

    protected Animal(float aPeso, LocalDate aFechaNacimiento, Sexo aSexo, String aRaza) {
        if (aPeso <= 0) throw new IllegalArgumentException("El peso debe ser > 0");
        peso = aPeso;
        fechaNacimiento = aFechaNacimiento;
        sexo = aSexo;
        raza = aRaza;
    }

    /** Consumo total de alimento por día (kg) */
    public abstract float getConsumoTotalKg();

    /** Devuelve el desglose de raciones por componente (por día) */
    public abstract Racion[] getDieta();

    public float getPeso() { return peso; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public Sexo getSexo() { return sexo; }
    public String getRaza() { return raza; }

    /** Valor energético total de la dieta (Mcal/día) */
    public float getValorEnergeticoTotalMcal() {
        float total = 0f;
        for (Racion r : getDieta()) {
            total += r.getAlimento().getEnergiaMcalKg() * r.getCantidadKilogramos(); // o r.getEnergiaMcal()
        }
        return redondear(total);
    }

/** Densidad energética (Mcal/kg) = energía total / kg totales */
    public float getDensidadEnergeticaMcalKg() {
        float kgTotales = getConsumoTotalKg();
        return kgTotales == 0 ? 0 : redondear(getValorEnergeticoTotalMcal() / kgTotales);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ Raza ").append(getRaza())
        .append(", Peso ").append(getPeso()).append("kg")
        .append(", Sexo ").append(getSexo())
        .append(" ]\n");
        sb.append("[ Consumo total: ").append(getConsumoTotalKg()).append(" kg/día ]\n");

        // NUEVO: energía total y densidad
        sb.append("[ Energía total: ").append(getValorEnergeticoTotalMcal()).append(" Mcal/día ]\n");
        sb.append("[ Densidad energética: ").append(getDensidadEnergeticaMcalKg()).append(" Mcal/kg ]\n");

        sb.append("[ Dieta ]");
        for (Racion r : getDieta()) {
            sb.append("\n").append(r);
        }
        return sb.toString();
}

    /** Utilidad para construir raciones según porcentajes */
    protected static Racion racion(Alimento alimento, float totalKg, float porcentaje) {
        return new Racion(alimento, redondear(totalKg * porcentaje));
    }

    /** Redondeo simple a 2 decimales para que quede prolijo al imprimir */
    protected static float redondear(float v) {
        return Math.round(v * 100f) / 100f;
    }
}
