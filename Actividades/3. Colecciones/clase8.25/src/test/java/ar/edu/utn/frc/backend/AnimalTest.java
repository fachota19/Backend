package ar.edu.utn.frc.backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class AnimalTest {

    @Test
    void testConsumoPorcino() {
        Animal p = new Porcino(100f, LocalDate.now(), Sexo.MACHO, RazaPorcino.DUROC);
        assertEquals(200f, p.getConsumoTotalKg(), 0.01); // 2 * 100
    }

    @Test
    void testConsumoBovino() {
        Animal b = new Bovino(450f, LocalDate.now(), Sexo.HEMBRA, RazaBovino.ANGUS);
        assertEquals(675f, b.getConsumoTotalKg(), 0.01); // 1.5 * 450
    }

    @Test
    void testSumaRacionesIgualTotalPorcino() {
        Animal p = new Porcino(100f, LocalDate.now(), Sexo.MACHO, RazaPorcino.DUROC);
        float total = p.getConsumoTotalKg();
        float suma = 0f;
        for (Racion r : p.getDieta()) suma += r.getCantidadKilogramos();
        assertEquals(total, suma, 0.01);
    }

    @Test
    void testPesoInvalidoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
            new Porcino(0f, LocalDate.now(), Sexo.HEMBRA, RazaPorcino.KUNEKUNE)
        );
    }
}
