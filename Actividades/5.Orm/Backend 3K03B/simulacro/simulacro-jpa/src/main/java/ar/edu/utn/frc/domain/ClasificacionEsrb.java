package ar.edu.utn.frc.domain;

public enum ClasificacionEsrb {
    E("E"), E10_PLUS("E10+"), T("T"), M("M"), AO("AO"), RP("RP");

    private final String code;
    ClasificacionEsrb(String code){ this.code = code; }
    public String code(){ return code; }

    public static ClasificacionEsrb fromCode(String code) {
        for (var v : values()) if (v.code.equals(code)) return v;
        throw new IllegalArgumentException("ESRB code inválido: " + code);
    }
}