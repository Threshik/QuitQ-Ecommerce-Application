package com.hexaware.quitQ_backend_1.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.quitQ_backend_1.dto.CategoryDto;
import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.service.interf.CategoryService;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
		return ResponseEntity.ok(categoryService.createCategory(categoryDto));
	}

	@PutMapping("/update/{categoryId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> updateCategory(@PathVariable Long categoryId,
			@RequestBody @Valid CategoryDto categoryDto) {
		return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDto));
	}

	@GetMapping("/get-all")
	//@PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'USER')")
	@PermitAll
	public ResponseEntity<Response> getAllCategories() {
		Response response = categoryService.getAllCategories();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getCategoryById/{categoryId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'USER')")
	public ResponseEntity<Response> getCategoryById(@PathVariable Long categoryId) {
		return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
	}

	@DeleteMapping("/deleteCategory/{categoryId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> deleteCategory(@PathVariable Long categoryId) {
		return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
	}
}
