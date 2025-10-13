package ejercicio.poo.e1;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MascotaVirtual {

    private final String nombre;
    private int energia;          // 0..100
    private int humor;            // 0..5
    private boolean durmiendo;
    private boolean viva;

    private int rachaIngestas;
    private int rachaActividades;

    // ---------- Constructor con invariantes ----------
    public MascotaVirtual(String nombre, int energiaInicial, int humorInicial) {
        this.nombre = nombre;
        this.energia = clamp(energiaInicial, 0, 100);
        this.humor = clamp(humorInicial, 1, 5);
        this.viva = (this.energia > 0);
        this.durmiendo = (this.humor == 0);
        this.rachaIngestas = 0;
        this.rachaActividades = 0;
    }

    // ---------- Comportamientos de ingesta ----------
    public boolean comer() {
        if (!puedeActuar()) return false;
        int delta = Math.round(this.energia * 0.10f);
        sumarEnergia(delta);

        rachaIngestas++;
        rachaActividades = 0;

        if (rachaIngestas >= 3) modificarHumor(-1);
        else modificarHumor(+1);

        if (rachaIngestas >= 5) morir();
        return true;
    }

    public boolean beber() {
        if (!puedeActuar()) return false;
        int delta = Math.round(this.energia * 0.05f);
        sumarEnergia(delta);

        rachaIngestas++;
        rachaActividades = 0;

        if (rachaIngestas >= 3) modificarHumor(-1);
        else modificarHumor(+1);

        if (rachaIngestas >= 5) morir();
        return true;
    }

    // ---------- Comportamientos de actividad ----------
    public boolean correr() {
        if (!puedeActuar()) return false;
        int delta = Math.round(this.energia * 0.35f);
        restarEnergia(delta);
        modificarHumor(-2);

        rachaActividades++;
        rachaIngestas = 0;

        if (rachaActividades >= 3 && viva) dormirForzadoPorActividad();
        return true;
    }

    public boolean saltar() {
        if (!puedeActuar()) return false;
        int delta = Math.round(this.energia * 0.15f);
        restarEnergia(delta);
        modificarHumor(-2);

        rachaActividades++;
        rachaIngestas = 0;

        if (rachaActividades >= 3 && viva) dormirForzadoPorActividad();
        return true;
    }

    // ---------- Otros comportamientos ----------
    public boolean dormir() {
        if (!viva || durmiendo) return false;
        durmiendo = true;
        rachaIngestas = 0;
        rachaActividades = 0;
        sumarEnergia(25);
        modificarHumor(+2);
        return true;
    }

    public boolean despertar() {
        if (!viva || !durmiendo) return false;
        durmiendo = false;
        rachaIngestas = 0;
        rachaActividades = 0;
        modificarHumor(-1);
        if (humor == 0) durmiendo = true;
        return true;
    }

    // ---------- Apoyo / reglas ----------
    private boolean puedeActuar() {
        return viva && !durmiendo;
    }

    private void sumarEnergia(int delta) {
        if (delta <= 0) return;
        energia = clamp(energia + delta, 0, 100);
        if (energia == 0) morir();
    }

    private void restarEnergia(int delta) {
        if (delta <= 0) return;
        energia = clamp(energia - delta, 0, 100);
        if (energia == 0) morir();
    }

    private void modificarHumor(int delta) {
        humor = clamp(humor + delta, 0, 5);
        if (humor == 0) durmiendo = true;
    }

    private void morir() {
        viva = false;
        durmiendo = false;
    }

    private void dormirForzadoPorActividad() {
        durmiendo = true;
        rachaIngestas = 0;
        rachaActividades = 0;
    }

    private static int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }
}
