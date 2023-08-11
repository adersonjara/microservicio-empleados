package com.app.msvc.empleados.repositories;

import org.springframework.data.repository.CrudRepository;

import com.app.msvc.empleados.domain.Empleado;

public interface EmpleadoRepository extends CrudRepository<Empleado, Long>{

}
