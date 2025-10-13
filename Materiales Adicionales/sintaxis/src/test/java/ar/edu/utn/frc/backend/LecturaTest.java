package ar.edu.utn.frc.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class LecturaTest {

	@Test
	public void elTotalEsCorrecto() throws FileNotFoundException {
		int resultado = Lectura.calcularTotal(URLDecoder.decode(ClassLoader.getSystemResource("numeros.txt").getPath(), StandardCharsets.UTF_8));
		assertEquals(resultado, 1030);
	}

	@Test
	public void elPromedioEsCorrecto() throws FileNotFoundException {
		float resultado = Lectura.calcularPromedio(URLDecoder.decode(ClassLoader.getSystemResource("numeros.txt").getPath(), StandardCharsets.UTF_8));
		assertEquals(resultado, 128.75f);
	}
}
