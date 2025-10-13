package ar.edu.utn.frc.backend.dominio.repositorio;

import java.util.List;

import ar.edu.utn.frc.backend.dominio.modelo.TipoAuto;

public interface TipoAutoRepository {
	List<TipoAuto> getAll();
}
