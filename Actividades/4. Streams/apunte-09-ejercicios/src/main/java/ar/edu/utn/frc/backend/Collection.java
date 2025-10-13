package ar.edu.utn.frc.backend;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ar.edu.utn.frc.backend.modelo.Auto;

public class Collection {

	/*
	 * Devuelve la cantidad de autos de una marca y un año determinado
	 *
	 * @param autos array de autos
	 * @param marca marca a buscar
	 * @param anio año a buscar
	 * @return cantidad de autos de una marca y un año determinado
	 */
	public static long obtenerCantidadPorMarcaYAnio(List<Auto> autos, String marca, int anio) {
		return autos.stream()
			.filter(a -> a.getAnio() == anio)
			.filter(a -> a.getMarca() != null && a.getMarca().equalsIgnoreCase(marca))
			.count();
	}


	/*
	 * Devuelve la cantidad de autos convertibles
	 *
	 * @param autos array de autos
	 * @return cantidad de autos convertibles
	 */
	public static long obtenerCantidadDeConvertibles(List<Auto> autos) {
		return 0;
	}

	/*
	 * Devuelve un array con las marcas que vendan sedanes
	 *
	 * @param autos array de autos
	 * @return array de marcas
	 */
	public static Set<String> obtenerLasMarcasQueVendanSedanes(List<Auto> autos) {
		return Collections.emptySet();
	}

	/*
	 * Devuelve un map con la cantidad de autos por marca
	 *
	 * @param autos array de autos
	 * @return map con la cantidad de autos por marca
	 */
	public static Map<String, Long> obtenerCantidadDeAutosPorMarca(List<Auto> autos) {
		return Collections.emptyMap();
	}

	/*
	 * Devuelve un map con la cantidad de autos por año
	 *
	 * @param autos array de autos
	 * @return map con la cantidad de autos por año
	 */
	public static Map<Integer, Long> obtenerCantidadDeAutosPorAnio(List<Auto> autos) {
		return Collections.emptyMap();

	}

	/*
	 * Devuelve los autos filtrados
	 * @param autos array de autos
	 * @param filtro filtro a aplicar
	 * @return autos filtrados
	 */
	public static List<Auto> filtrarAutos(List<Auto> autos, Predicate<Auto> filtro) {
		return Collections.emptyList();
	}

	/**
	 * Devuelve una lista de autos que no sean del tipo especificado
	 *
	 * @param autos array de autos
	 * @param tipo  tipo a filtrar
	 * @return lista de autos que no sean del tipo especificado
	 */
	public static List<Auto> obtenerTodosLosAutosQueNoSeanDelTipo(List<Auto> autos, String tipo) {
		return Collections.emptyList();
	}

	/*
	 * Obtener las marcas que tengan modelos con números en el nombre
	 * @param autos array de autos
	 * @return marcas que tengan modelos con números en el nombre
	 */
	public static Set<String> obtenerLasMarcasQueTenganModelosConNumeros(List<Auto> autos) {
		return Collections.emptySet();
	}

	/*
	 * Leer el archivo autos.csv y devuelve un array de autos
	 * @return array de autos
	 */
	public static List<Auto> obtenerAutos() {
		String path = URLDecoder.decode(
			ClassLoader.getSystemResource("autos.csv").getPath(),
			StandardCharsets.UTF_8
		);

		try (Stream<String> lineas = Files.lines(new File(path).toPath())) {
			return lineas
				.filter(l -> l != null && !l.isBlank())
				.map(Auto::fromString)                    
				.collect(Collectors.toList());            
		} catch (IOException e) {
			// No cambiamos la firma del método: si hay problema de E/S,
			// lanzamos RuntimeException para que el test lo evidencie.
			throw new RuntimeException("No se pudo leer autos.csv", e);
		}
	}

}
