package ar.edu.utn.frc.backend;

import java.util.Arrays;

public class Dieta {

	private final Racion[] raciones;

	public Dieta(Racion[] raciones) {
		this.raciones = raciones;
	}

	public Racion[] getRaciones() {
		return raciones;
	}

	@Override public String toString() {
		return "Dieta{" +
			"raciones=" + Arrays.toString(raciones) +
			'}';
	}
}
