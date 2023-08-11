package com.app.msvc.empleados.services;

import java.util.List;
import java.util.Optional;

import com.app.msvc.empleados.domain.Empleado;

public interface EmpleadoService {
	
	List<Empleado> listar();

	Empleado guardar(Empleado empleado);
	
	Optional<Empleado> porID(Long id);
	
	void eliminar(Long id);
}
