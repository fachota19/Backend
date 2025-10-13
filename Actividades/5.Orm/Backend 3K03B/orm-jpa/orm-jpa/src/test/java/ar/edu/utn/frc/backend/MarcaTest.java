package ar.edu.utn.frc.backend;

import ar.edu.utn.frc.backend.dominio.modelo.Marca;
import ar.edu.utn.frc.backend.dominio.repositorio.MarcaRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarcaTest {
	private MarcaRepository marcaRepository;

	@Test
	public void obtieneLaCantidadMarcasCorrectamente() {
		assertEquals(9, marcaRepository.getAll().size());
	}

	@Test
	public void laMarcaAudiEsCorrecta() {
		final Marca marcaEsperada = new Marca(6, "Audi");
		final Marca marca = marcaRepository
			.getAll()
			.stream()
			.filter(tipo -> tipo.getNombre().equalsIgnoreCase("Audi"))
			.findFirst()
			.orElseThrow();
		assertEquals(marcaEsperada, marca);
	}
}
