package com.hexaware.quitQ_backend_1.service.interf;

import com.hexaware.quitQ_backend_1.dto.CategoryDto;
import com.hexaware.quitQ_backend_1.dto.Response;

public interface CategoryService {

	Response createCategory(CategoryDto categoryRequest);

	Response updateCategory(Long categoryId, CategoryDto categoryRequest);

	Response getAllCategories();

	Response getCategoryById(Long categoryId);

	Response deleteCategory(Long categoryId);

	Long getCategoryCount();

}
