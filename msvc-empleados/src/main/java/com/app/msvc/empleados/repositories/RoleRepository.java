package com.app.msvc.empleados.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.msvc.empleados.domain.ERole;
import com.app.msvc.empleados.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByName(ERole name);

}
