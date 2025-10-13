package ar.edu.utn.frc.backend.modelo;

import java.util.Collections;
import java.util.List;

public class Auto {
	private final int anio;
	private final String marca;
	private final String modelo;
	private final List<String> tipos;

	public Auto(int anio, String marca, String modelo, List<String> tipos) {
		this.anio = anio;
		this.marca = marca;
		this.modelo = modelo;
		this.tipos = tipos;
	}

	public int getAnio() {
		return anio;
	}

	public String getMarca() {
		return marca;
	}

	public String getModelo() {
		return modelo;
	}

	public List<String> getTipos() {
		return Collections.unmodifiableList(tipos);
	}

	public static Auto fromString(String linea) {
		String[] campos = linea.split(",");
		return new Auto(Integer.parseInt(campos[0]),
			campos[1],
			campos[2],
			List.of(campos[3].split("\\|")));
	}
}
