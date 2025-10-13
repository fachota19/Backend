package ar.edu.utn.frc.backend;

import java.time.LocalDate;

public class Bovino extends Animal {

	public Bovino(float aPeso, LocalDate aFechaNacimiento, Sexo aSexo, RazaBovino aRaza) {
		super(aPeso, aFechaNacimiento, aSexo, aRaza.getNombre());
	}

	@Override public Dieta getDieta() {

		final float consumoTotal = this.getPeso() * 1.5f;

		return new Dieta(
			new Racion[] {
				new Racion(Alimento.MAIZ, consumoTotal * 0.4f),
				new Racion(Alimento.SOJA, consumoTotal * 0.3f),
				new Racion(Alimento.FORRAJE, consumoTotal * 0.3f)
			}
		);
	}
}
