package com.app.msvc.empleados.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.msvc.empleados.domain.Empleado;
import com.app.msvc.empleados.services.EmpleadoService;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin
@RequestMapping("/api/empleados")
@RestController
public class EmpleadoController {
	
	@Autowired
    private EmpleadoService empleadoService;
	
	
	@GetMapping
	@Operation(summary = "Lista de empleados")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> listar() {
        try {
            List<Empleado> empleados = empleadoService.listar();
            return ResponseEntity.ok(empleados);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("error", Collections.singletonList("Error al listar empleados: " + ex.getMessage())));
        }
    }
	
	@GetMapping("/{id}")
	@Operation(summary = "Datos de un empleado")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> detalle(@PathVariable Long id){
		
		try {
			Optional<Empleado> empleadoOptional = empleadoService.porID(id);
	        if (empleadoOptional.isPresent()){
	            return ResponseEntity.ok(empleadoOptional.get());
	        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", Collections.singletonList("No se encontró el recurso")));
            }
	        
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", Collections.singletonList("Error no se encontro el recurso: " + ex.getMessage())));
        }
    }
	
	@PostMapping
	@Operation(summary = "Registro de un empleado")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> crear(@Valid @RequestBody Empleado empleado, BindingResult result){     
        try {
        	if (result.hasErrors()){
                return validar(result);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.guardar(empleado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", Collections.singletonList("Error no se creo correctamente el recurso: " + ex.getMessage())));
        }
    }
	
	@PutMapping("/{id}")
	@Operation(summary = "Edición de datos de un empleado")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> editar(@Valid @RequestBody Empleado empleado, BindingResult result,@PathVariable Long id){
        try {
        	if (result.hasErrors()){
                return validar(result);
            }
        	
        	Optional<Empleado> empleadoOptional = empleadoService.porID(id);
            if (empleadoOptional.isPresent()){
                Empleado empleadoDb = empleadoOptional.get();

                

                empleadoDb.setNombres(empleado.getNombres());
                empleadoDb.setApellidos(empleado.getApellidos());
                empleadoDb.setDireccion(empleado.getDireccion());
                return ResponseEntity.status(HttpStatus.OK).body(empleadoService.guardar(empleadoDb));
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", Collections.singletonList("No se encontró el recurso")));
            }
        	
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", Collections.singletonList("Error no se actualizo correctamente el recurso: " + ex.getMessage())));
        }
    }
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminación de un registro de empleado")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try {
        	Optional<Empleado> empleadoOptional = empleadoService.porID(id);
            if (empleadoOptional.isPresent()){
            	empleadoService.eliminar(id);
                return ResponseEntity.noContent().build();
            }else {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", Collections.singletonList("No se encontró el recurso")));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", Collections.singletonList("Error: " + ex.getMessage())));
        }
    }
	
	private ResponseEntity<?> validar(BindingResult result){
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(),"El campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
