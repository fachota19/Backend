package ar.edu.utn.frc.backend;

public enum Alimento {
    MAIZ(3.40f),
    FORRAJE(2.00f),
    TRIGO(3.20f),
    SOJA(3.00f),
    SORGO(0.0f); // no se usa en estas consignas; lo dejamos en 0 por defecto

    private final float energiaMcalKg;

    Alimento(float energiaMcalKg) {
        this.energiaMcalKg = energiaMcalKg;
    }

    public float getEnergiaMcalKg() {
        return energiaMcalKg;
    }
}
