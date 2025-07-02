package com.hexaware.quitQ_backend_1.service.implementation;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.quitQ_backend_1.dto.ProductDto;
import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.dto.SellerProductDTO;
import com.hexaware.quitQ_backend_1.dto.SellerProductDetailsDTO;
import com.hexaware.quitQ_backend_1.entities.Category;
import com.hexaware.quitQ_backend_1.entities.Product;
import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.enums.Role;
import com.hexaware.quitQ_backend_1.exception.ResourceNotFoundException;
import com.hexaware.quitQ_backend_1.mapper.EntityDtoMapper;
import com.hexaware.quitQ_backend_1.repository.CategoryRepository;
import com.hexaware.quitQ_backend_1.repository.ProductRepository;
import com.hexaware.quitQ_backend_1.repository.UserRepository;
import com.hexaware.quitQ_backend_1.service.AwsS3Service;
import com.hexaware.quitQ_backend_1.service.interf.ProductService;
import com.hexaware.quitQ_backend_1.service.interf.UserService;

@Service
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepo;
	private final CategoryRepository categoryRepo;
	private final EntityDtoMapper entityDtoMapper;
	private final UserService userService;
	private final AwsS3Service awsS3Service;
	private final UserRepository userRepository;

	

	public ProductServiceImpl(ProductRepository productRepo, CategoryRepository categoryRepo,
			EntityDtoMapper entityDtoMapper, UserService userService, AwsS3Service awsS3Service,
			UserRepository userRepository) {
		super();
		this.productRepo = productRepo;
		this.categoryRepo = categoryRepo;
		this.entityDtoMapper = entityDtoMapper;
		this.userService = userService;
		this.awsS3Service = awsS3Service;
		this.userRepository = userRepository;
	}

	@Override
	public Response createProduct(Long categoryId, MultipartFile image, String name, String description,
			BigDecimal price, String brand, int inventory) {
		// TODO Auto-generated method stub
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		User seller = userService.getLoginUser();
		if (image == null || image.isEmpty()) {
			throw new IllegalArgumentException("Product image is required");
		}
		String productImageUrl = awsS3Service.saveImageToS3(image);

		Product product = new Product();
		product.setCategory(category);
		product.setPrice(price);
		product.setProductName(name);
		product.setDescription(description);
		product.setImageUrl(productImageUrl);
		product.setBrand(brand);
		product.setInventory(inventory);
		product.setSeller(seller);

		productRepo.save(product);

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Product successfully created");
		return response;
	}

	@Override
	public Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description,
			BigDecimal price, String brand, int inventory) {
		// TODO Auto-generated method stub
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
		Category category = null;
		String productImageUrl = null;

		if (categoryId != null) {
			category = categoryRepo.findById(categoryId)
					.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		}
		if (image != null && !image.isEmpty()) {
			productImageUrl = awsS3Service.saveImageToS3(image);
		}

		if (category != null)
			product.setCategory(category);
		if (name != null)
			product.setProductName(name);
		if (price != null)
			product.setPrice(price);
		if (description != null)
			product.setDescription(description);
		if (productImageUrl != null)
			product.setImageUrl(productImageUrl);
		if (brand != null)
			product.setBrand(brand);
		if (inventory != 0)
			product.setInventory(inventory);

		productRepo.save(product);

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Product updated successfully");
		return response;

	}


	@Override
	public Response getProductById(Long productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
		ProductDto productDto = EntityDtoMapper.toProductDto(product);

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Fetched Product from Id successfully");
		response.setProduct(productDto);
		return response;
	}

	@Override
	public Response getAllProducts() {
		// TODO Auto-generated method stub
		List<Product> products = productRepo.findAll();
		List<ProductDto> productDtoList = new ArrayList<>();
		for (Product product : products) {
			productDtoList.add(EntityDtoMapper.toProductDto(product));
		}
		Response response = new Response();
		response.setStatus(200);
		response.setMessage("All products retrieved successfully");
		response.setProductList(productDtoList);
		return response;
	}

	@Override
	public Response getProductsByCategory(Long categoryId) {
		// TODO Auto-generated method stub
		Optional<Category> categoryOpt = categoryRepo.findById(categoryId);

		if (!categoryOpt.isPresent()) {
			Response response = new Response();
			response.setStatus(404);
			response.setMessage("Category not found");
			return response;
		}

		Category category = categoryOpt.get();
		List<Product> productList = category.getProductList();
		List<ProductDto> productDtoList = new ArrayList<>();

		for (Product product : productList) {
			productDtoList.add(EntityDtoMapper.toProductDto(product));
		}

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Products under category retrieved successfully");
		response.setProductList(productDtoList);
		return response;

	}

	@Override
	public Response getSellerProductCount(Long sellerId) {
		int count = productRepo.countBySellerId(sellerId);
		
		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Product count fetched successfully.");
		response.setCount(count);
		return response;
	}

	@Override
	public Response getSellerProducts(Long sellerId) {
		List<Product> products = productRepo.findBySellerId(sellerId);
		List<ProductDto> productDtos = new ArrayList<>();

		for (Product product : products) {
			productDtos.add(EntityDtoMapper.toProductDto(product));
		}
		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Seller products fetched successfully.");
		response.setProductList(productDtos);
		return response;
	}

	@Override
	public Response deleteProduct(Long productId, Long sellerId) {
		Optional<Product> productOpt = productRepo.findById(productId);
		Response response = new Response();

		if (productOpt.isEmpty()) {
			response.setStatus(404);
			response.setMessage("Product not found.");
			return response;
		}

		Product product = productOpt.get();

		if (!product.getSeller().getId().equals(sellerId)) {
			response.setStatus(403);
			response.setMessage("You are not authorized to delete this product.");
			return response;
		}

		productRepo.delete(product);

		response.setStatus(200);
		response.setMessage("Product deleted successfully.");
		return response;
	}

	@Override
	public Long getProductCount() {
		return productRepo.count();
	}
	
	@Override
	public Response getAllSellersWithProducts() {
	    List<User> sellers = userRepository.findByRole(Role.SELLER);

	    List<SellerProductDTO> sellersWithProducts = new ArrayList<>();

	    for (User seller : sellers) {
	        SellerProductDetailsDTO details = new SellerProductDetailsDTO(
	            seller.getId(), seller.getName(), seller.getEmail(), 
	            seller.getShopName(), seller.getBusinessLicense(),
	            seller.getGstNumber(), seller.getShopAddress()
	        );

	        List<Product> products = productRepo.findBySellerId(seller.getId());

	        List<ProductDto> productDtos = new ArrayList<>();
	        for (Product product : products) {
	            productDtos.add(EntityDtoMapper.toProductDto(product));
	        }

	        SellerProductDTO dto = new SellerProductDTO();
	        dto.setSellerDetails(details);
	        dto.setProducts(productDtos); 

	        sellersWithProducts.add(dto);
	    }

	    Response response = new Response();
	    response.setStatus(HttpStatus.OK.value());
	    response.setMessage("Sellers and their products fetched");
	    response.setData(sellersWithProducts);
	    return response;
	}



}
