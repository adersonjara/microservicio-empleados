package com.app.msvc.empleados.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.msvc.empleados.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

}
