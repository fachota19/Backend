package ar.edu.utn.frc.backend;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import ar.edu.utn.frc.backend.modelo.Auto;

public class CollectionTest {

	@Test
	public void devuelveLaCantidadCorrecta() {

		List<Auto> autos = Collection.obtenerAutos();

		long resultado = Collection.obtenerCantidadPorMarcaYAnio(autos, "Audi", 2020);

		assertEquals(7, resultado);
	}


	@Test
	public void devuelveLaCantidadCorrecta2() {

		List<Auto> autos = Collection.obtenerAutos();

		long resultado = Collection.obtenerCantidadPorMarcaYAnio(autos, "BMW", 2021);

		assertEquals(17, resultado);
	}

	@Test
	public void devuelveLaCantidadDeConvertibles() {

		List<Auto> autos = Collection.obtenerAutos();

		long resultado = Collection.obtenerCantidadDeConvertibles(autos);

		assertEquals(14, resultado);
	}

	@Test
	public void devuelveLaCantidadMarcasQueVendanSedanes() {

		List<Auto> autos = Collection.obtenerAutos();

		Set<String> resultado = Collection.obtenerLasMarcasQueVendanSedanes(autos);

		assertArrayEquals(new String[] { "Acura", "Audi", "Alfa Romeo", "BMW", "Bentley" }, resultado.toArray());
	}

	@Test
	public void devuelveLaCantidadCorrectaDeAudi() {

		List<Auto> autos = Collection.obtenerAutos();

		Map<String, Long> resultado = Collection.obtenerCantidadDeAutosPorMarca(autos);

		assertEquals(31, resultado.get("Audi"));
	}

	@Test
	public void devuelveLaCantidadCorrectaDeFerrari() {

		List<Auto> autos = Collection.obtenerAutos();

		Map<String, Long> resultado = Collection.obtenerCantidadDeAutosPorMarca(autos);

		assertEquals(3, resultado.get("Ferrari"));
	}

	@Test
	public void devuelveLaCantidadCorrectaDeAutosPorAnio() {

		List<Auto> autos = Collection.obtenerAutos();

		Map<Integer, Long> resultado = Collection.obtenerCantidadDeAutosPorAnio(autos);

		assertEquals(20, resultado.get(2019));
	}

	@Test
	public void filtraCorrectamente() {

		List<Auto> autos = Collection.obtenerAutos();

		List<Auto> resultado = Collection.filtrarAutos(autos, (auto) -> auto.getMarca().equalsIgnoreCase("Ferrari"));

		assertEquals(3, resultado.size());
	}

	@Test
	public void filtraPorTipoCorrectamente() {

		List<Auto> autos = Collection.obtenerAutos();

		List<Auto> resultado = Collection.obtenerTodosLosAutosQueNoSeanDelTipo(autos, "SUV");

		assertEquals(56, resultado.size());
	}

	@Test
	public void obtieneMarcasConModelosConNumerosCorrectamente(){
		List<Auto> autos = Collection.obtenerAutos();

		Set<String> resultado = Collection.obtenerLasMarcasQueTenganModelosConNumeros(autos);

		assertArrayEquals(new String[] { "Ferrari", "FIAT", "Audi", "Ford", "Alfa Romeo", "BMW", "Aston Martin" }, resultado.toArray());
	}
}
