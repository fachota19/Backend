package com.frc.backend.gamesapp.modelo;

public enum ClasificacionESRB {
    E("Everyone"),
    E10P("Everyone 10+"),
    T("Teen"),
    M("Mature"),
    AO("Adults Only"),
    RP("Rating Pending"),
    UR("Unrated");

    private final String descripcion;

    ClasificacionESRB(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static ClasificacionESRB fromString(String valor) {
        if (valor == null || valor.isBlank()) return UR;

        valor = valor.trim();

        return switch (valor.toUpperCase()) {
            case "E", "EVERYONE" -> E;
            case "E10+", "EVERYONE 10+" -> E10P;
            case "T", "TEEN" -> T;
            case "M", "MATURE" -> M;
            case "AO", "ADULTS ONLY" -> AO;
            case "RP", "RATING PENDING" -> RP;
            default -> UR;
        };
    }
}
