package ar.edu.utn.frc.backend;

import ar.edu.utn.frc.backend.dominio.modelo.Marca;
import ar.edu.utn.frc.backend.dominio.modelo.Modelo;
import ar.edu.utn.frc.backend.dominio.modelo.TipoAuto;
import ar.edu.utn.frc.backend.dominio.repositorio.ModeloRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModeloTest {

	ModeloRepository modeloRepository;

	@Test
	public void obtieneElModeloCorrecto() {
		final Modelo modeloEsperado = new Modelo(
			50,
			"2 Series",
			2021,
			new Marca(8, "BMW"),
			List.of(
				new TipoAuto(1, "Sedan"),
				new TipoAuto(3, "Convertible"),
				new TipoAuto(6, "Coupe")
			)
		);
		final Modelo modelo = modeloRepository.get(50);

		assertEquals(modeloEsperado, modelo);
	}
}
