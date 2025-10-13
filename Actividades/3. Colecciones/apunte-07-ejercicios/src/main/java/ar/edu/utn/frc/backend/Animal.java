package ar.edu.utn.frc.backend;

import java.time.LocalDate;

public abstract class Animal {
	protected float peso;
	private final LocalDate fechaNacimiento;
	private final Sexo sexo;
	private final String raza;

	public Animal(float aPeso, LocalDate aFechaNacimiento, Sexo aSexo, String aRaza) {
		peso = aPeso;
		fechaNacimiento = aFechaNacimiento;
		sexo = aSexo;
		raza = aRaza;
	}

	public float getPeso() {
		return peso;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public String getRaza() {
		return raza;
	}

	public abstract Dieta getDieta();

	@Override
	public String toString() {

		return "[ Raza " + raza + ", Peso" + peso + "kg, Sexo " + sexo + " ]\n" +
			"[ Dieta ]" + "\n" + getDieta();
	}
}
