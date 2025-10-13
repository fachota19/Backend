package ar.edu.utn.frc.backend;

public enum RazaPorcino {
    DUROC("Duroc"),
    KUNEKUNE("Kunekune"),
    HAMPSHIRE("Cerdo Hampshire");

    private final String nombre;

    RazaPorcino(final String aNombre) {
        nombre = aNombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
