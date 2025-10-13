package ar.edu.utn.frc.backend.dominio.repositorio;

import java.util.List;

import ar.edu.utn.frc.backend.dominio.modelo.Marca;

public interface MarcaRepository {
	List<Marca> getAll();
}
