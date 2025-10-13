package ar.edu.utn.frc.backend;

import ar.edu.utn.frc.backend.dominio.modelo.TipoAuto;
import ar.edu.utn.frc.backend.dominio.repositorio.TipoAutoRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipoAutoTest {

	private TipoAutoRepository tipoAutoRepository;

	@Test
	public void obtieneLaCantidadTiposDeAutosCorrectamente() {

		assertEquals(7, tipoAutoRepository.getAll().size());
	}

	@Test
	public void elTipoConvertibleEsCorrecto() {
		final TipoAuto tipoAutoEsperado = new TipoAuto(3, "Convertible");
		final TipoAuto tipoAuto = tipoAutoRepository
			.getAll()
			.stream()
			.filter(tipo -> tipo.getNombre().equalsIgnoreCase("Convertible"))
			.findFirst()
			.orElseThrow();
		assertEquals(tipoAutoEsperado, tipoAuto);
	}
}
