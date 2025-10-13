package ar.edu.utn.frc.backend;

public final class Racion {
	private final Alimento alimento;
	private final float cantidadKilogramos;

	public Racion(final Alimento aAlimento, final float aCantidadKilogramos) {
		alimento = aAlimento;
		cantidadKilogramos = aCantidadKilogramos;
	}

	public Alimento getAlimento() {
		return alimento;
	}

	public float getCantidadKilogramos() {
		return cantidadKilogramos;
	}

	@Override
	public String toString() {
		return "[ " + cantidadKilogramos + "kg de " + alimento + " ]";
	}
}
