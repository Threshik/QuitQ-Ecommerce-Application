package com.hexaware.quitQ_backend_1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.quitQ_backend_1.dto.UserDto;
import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.enums.Role;


public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email); //login
	boolean existsByEmail(String email);
	List<User> findByRole(Role role); //Admin fetch
	List<User> findByNameContainingIgnoreCase(String name); //admin dashboard
	
}
