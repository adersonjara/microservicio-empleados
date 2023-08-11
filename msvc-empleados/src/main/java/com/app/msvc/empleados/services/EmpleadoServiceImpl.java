package com.app.msvc.empleados.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.msvc.empleados.domain.Empleado;
import com.app.msvc.empleados.repositories.EmpleadoRepository;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{
	
	@Autowired
    private EmpleadoRepository empleadoRepository;
	
	@Override
    @Transactional
    public List<Empleado> listar() {
        return (List<Empleado>) empleadoRepository.findAll();
    }

	@Override
	@Transactional
	public Empleado guardar(Empleado empleado) {
		return empleadoRepository.save(empleado);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Empleado> porID(Long id) {
		return empleadoRepository.findById(id);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {		
		empleadoRepository.deleteById(id);	
	}

}

