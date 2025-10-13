package ar.edu.utn.frc.backend;

import java.time.LocalDate;

public class Porcino  extends Animal {
	public Porcino(float aPeso, LocalDate aFechaNacimiento, Sexo aSexo, RazaPorcino aRaza) {
		super(aPeso, aFechaNacimiento, aSexo, aRaza.getNombre());
	}

	@Override public Dieta getDieta() {

		final float consumoTotal = this.getPeso() * 2.0f;

		return new Dieta(
			new Racion[] {
				new Racion(Alimento.MAIZ, consumoTotal * 0.5f),
				new Racion(Alimento.SOJA, consumoTotal * 0.3f),
				new Racion(Alimento.TRIGO, consumoTotal * 0.2f)
			}
		);
	}
}
