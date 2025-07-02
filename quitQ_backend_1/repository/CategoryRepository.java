package com.hexaware.quitQ_backend_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.quitQ_backend_1.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	  Category findByName(String name);

	  boolean existsByName(String name);

}
