package com.hexaware.quitQ_backend_1.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hexaware.quitQ_backend_1.dto.CategoryDto;
import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.dto.UserDto;
import com.hexaware.quitQ_backend_1.entities.Category;
import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.exception.ResourceNotFoundException;
import com.hexaware.quitQ_backend_1.mapper.EntityDtoMapper;
import com.hexaware.quitQ_backend_1.repository.CategoryRepository;
import com.hexaware.quitQ_backend_1.service.interf.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	private final CategoryRepository categoryRepository;
	private final EntityDtoMapper entityDtoMapper;
	
	
	public CategoryServiceImpl(CategoryRepository categoryRepository, EntityDtoMapper entityDtoMapper) {
		super();
		this.categoryRepository = categoryRepository;
		this.entityDtoMapper = entityDtoMapper;
	}

	@Override
	public Response createCategory(CategoryDto categoryRequest) {
		// TODO Auto-generated method stub
		Category category = new Category();
		category.setName(categoryRequest.getName());
		categoryRepository.save(category);
		
		Response response = new Response();
	    response.setStatus(200);
	    response.setMessage("Category created successfully");
	    return response;
	}

	@Override
	public Response updateCategory(Long categoryId, CategoryDto categoryRequest) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Not Found"));
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        
		Response response = new Response();
	    response.setStatus(200);
	    response.setMessage("Category updated successfully");
	    return response;
	}

	@Override
	public Response getAllCategories() {
		// TODO Auto-generated method stub
		List<Category> catogories = categoryRepository.findAll();
		
		List<CategoryDto> categoryDtoList = new ArrayList<>();
	    for (Category category : catogories) {
	    	CategoryDto categoryDto = EntityDtoMapper.toCategoryDto(category);
	    	categoryDtoList.add(categoryDto);
	    }
		
		Response response = new Response();
	    response.setStatus(200);
	    response.setMessage("All Categories Fetched Successfully");
	    response.setCategoryList(categoryDtoList);
	    return response;
	}

	@Override
	public Response getCategoryById(Long categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Not Found"));
        CategoryDto categoryDto = EntityDtoMapper.toCategoryDto(category);
        
		Response response = new Response();
	    response.setStatus(200);
	    response.setMessage("Category fetched by id");
	    response.setCategory(categoryDto);
	    return response;
	}

	@Override
	public Response deleteCategory(Long categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Not Found"));
		categoryRepository.delete(category);
		
		Response response = new Response();
	    response.setStatus(200);
	    response.setMessage("Category was deleted successfully");
	    return response;
	}
	@Override
	public Long getCategoryCount() {
	    return categoryRepository.count();
	}

}
