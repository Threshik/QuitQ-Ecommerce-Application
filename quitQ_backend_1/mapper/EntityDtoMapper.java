package com.hexaware.quitQ_backend_1.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hexaware.quitQ_backend_1.dto.AddressDto;
import com.hexaware.quitQ_backend_1.dto.BasicUserDto;
import com.hexaware.quitQ_backend_1.dto.CartDto;
import com.hexaware.quitQ_backend_1.dto.CartItemDto;
import com.hexaware.quitQ_backend_1.dto.CategoryDto;
import com.hexaware.quitQ_backend_1.dto.OrderDto;
import com.hexaware.quitQ_backend_1.dto.OrderItemDto;
import com.hexaware.quitQ_backend_1.dto.PaymentDto;
import com.hexaware.quitQ_backend_1.dto.ProductDto;
import com.hexaware.quitQ_backend_1.dto.ReviewDto;
import com.hexaware.quitQ_backend_1.dto.SellerDetailsDto;
import com.hexaware.quitQ_backend_1.dto.UserDto;
import com.hexaware.quitQ_backend_1.entities.Address;
import com.hexaware.quitQ_backend_1.entities.Cart;
import com.hexaware.quitQ_backend_1.entities.CartItem;
import com.hexaware.quitQ_backend_1.entities.Category;
import com.hexaware.quitQ_backend_1.entities.Order;
import com.hexaware.quitQ_backend_1.entities.OrderItem;
import com.hexaware.quitQ_backend_1.entities.Payment;
import com.hexaware.quitQ_backend_1.entities.Product;
import com.hexaware.quitQ_backend_1.entities.Review;
import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.enums.AddressType;
import com.hexaware.quitQ_backend_1.enums.OrderStatus;
import com.hexaware.quitQ_backend_1.enums.Role;

@Component
public class EntityDtoMapper {

	// User <-> UserDto

	public static UserDto toUserDto(User user) {
		if (user == null)
			return null;
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setGender(user.getGender());
		dto.setPhoneNumber(user.getPhoneNumber());
		dto.setPassword(user.getPassword());
		dto.setRole(user.getRole());
		dto.setRegistrationDate(user.getRegistrationDate());
		dto.setAddress(toAddressDto(user.getAddress()));
		dto.setCart(toCartDto(user.getCart()));
		if (user.getRole() == Role.USER) {
			dto.setOrderDtos(toOrderDtoList(user.getOrders()));
		}

		if (user.getRole() == Role.SELLER) {
			SellerDetailsDto sellerDto = new SellerDetailsDto();
			sellerDto.setShopName(user.getShopName());
			sellerDto.setBusinessLicense(user.getBusinessLicense());
			sellerDto.setGstNumber(user.getGstNumber());
			sellerDto.setShopAddress(user.getShopAddress());
			dto.setSellerDetails(sellerDto);

		}
		return dto;
	}

	public static User toUser(UserDto dto) {
		if (dto == null)
			return null;
		User user = new User();
		user.setId(dto.getId());
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setGender(dto.getGender());
		user.setPhoneNumber(dto.getPhoneNumber());
		user.setPassword(dto.getPassword());
		user.setRole(dto.getRole());
		user.setRegistrationDate(dto.getRegistrationDate());
		user.setAddress(toAddress(dto.getAddress()));
		user.setCart(toCart(dto.getCart()));
		if (dto.getRole() == Role.USER) {
			user.setOrders(toOrderList(dto.getOrderDtos()));
		}

		if (user.getRole() == Role.SELLER) {
			SellerDetailsDto sellerDto = new SellerDetailsDto();
			sellerDto.setShopName(user.getShopName());
			sellerDto.setBusinessLicense(user.getBusinessLicense());
			sellerDto.setGstNumber(user.getGstNumber());
			sellerDto.setShopAddress(user.getShopAddress());
			dto.setSellerDetails(sellerDto);
		}
		return user;
	}

	// BasicUser <-> BasicUserDto
	public static BasicUserDto toBasicUserDto(User user) {
		if (user == null)
			return null;
		BasicUserDto dto = new BasicUserDto();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setPhoneNumber(user.getPhoneNumber());
		dto.setRole(user.getRole());
		return dto;
	}

	public static User toBasicUser(BasicUserDto dto) {
		if (dto == null)
			return null;
		User user = new User();
		user.setId(dto.getId());
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPhoneNumber(dto.getPhoneNumber());
		user.setRole(dto.getRole());
		return user;
	}

	// Address <-> AddressDto
	public static AddressDto toAddressDto(Address address) {
		if (address == null)
			return null;
		AddressDto dto = new AddressDto();
		dto.setId(address.getId());
		dto.setStreet(address.getStreet());
		dto.setCity(address.getCity());
		dto.setState(address.getState());
		dto.setZipCode(address.getZipCode());
		dto.setCountry(address.getCountry());
		dto.setAddressType(address.getAddressType());
		dto.setCreatedAt(address.getCreatedAt());
		// dto.setBacBasicUserDto(toBasicUserDto(address.getUser()));
		return dto;
	}

	public static Address toAddress(AddressDto dto) {
		if (dto == null)
			return null;
		Address address = new Address();
		address.setId(dto.getId());
		address.setStreet(dto.getStreet());
		address.setCity(dto.getCity());
		address.setState(dto.getState());
		address.setZipCode(dto.getZipCode());
		address.setCountry(dto.getCountry());
		address.setAddressType(dto.getAddressType());
		address.setCreatedAt(dto.getCreatedAt());
		// address.setUser(toBasicUser(dto.getBacBasicUserDto()));
		return address;
	}

	public static CategoryDto toCategoryDto(Category category) {
		if (category == null)
			return null;
		CategoryDto dto = new CategoryDto();
		dto.setId(category.getId());
		dto.setName(category.getName());
		if (category.getProductList() != null) {
			List<ProductDto> productDtoList = new ArrayList<>();
			for (Product product : category.getProductList()) {
				productDtoList.add(toProductDto(product));
			}
			dto.setProductList(productDtoList);
		}
		return dto;

	}

	public static Category toCategory(CategoryDto dto) {
		if (dto == null)
			return null;
		Category category = new Category();
		category.setId(dto.getId());
		category.setName(dto.getName());
		if (dto.getProductList() != null) {
			List<Product> products = new ArrayList<>();
			for (ProductDto productDto : dto.getProductList()) {
				Product product = toProduct(productDto);
				products.add(product);
			}
			category.setProductList(products);
		} else {
			category.setProductList(null);
		}
		return category;

	}

	// Product <-> ProductDto
	public static ProductDto toProductDto(Product product) {
		if (product == null)
			return null;
		ProductDto dto = new ProductDto();
		dto.setId(product.getId());
		dto.setProductName(product.getProductName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setBrand(product.getBrand());
		dto.setImageUrl(product.getImageUrl());
		dto.setCreatedAt(product.getCreatedAt());
		dto.setInventory(product.getInventory());
		if (product.getCategory() != null) {
			dto.setCategoryId(product.getCategory().getId());
			dto.setCategoryName(product.getCategory().getName());
		}
		if (product.getSeller() != null && product.getSeller().getRole() == Role.SELLER) {
			SellerDetailsDto sellerDto = new SellerDetailsDto();
			sellerDto.setShopName(product.getSeller().getShopName());
			sellerDto.setBusinessLicense(product.getSeller().getBusinessLicense());
			sellerDto.setGstNumber(product.getSeller().getGstNumber());
			sellerDto.setShopAddress(product.getSeller().getShopAddress());
			dto.setSellerDetails(sellerDto);
		}
		return dto;
	}

	public static Product toProduct(ProductDto dto) {
		if (dto == null)
			return null;
		Product product = new Product();
		product.setId(dto.getId());
		product.setProductName(dto.getProductName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setBrand(dto.getBrand());
		product.setImageUrl(dto.getImageUrl());
		product.setCreatedAt(dto.getCreatedAt());
		product.setInventory(dto.getInventory());
		if (dto.getCategoryId() != null) {
			Category category = new Category();
			category.setId(dto.getCategoryId());
			category.setName(dto.getCategoryName());
			product.setCategory(category);
		}

		if (dto.getSellerDetails() != null) {
			User seller = new User();
			seller.setRole(Role.SELLER); // Assuming you have Role enum
			seller.setShopName(dto.getSellerDetails().getShopName());
			seller.setBusinessLicense(dto.getSellerDetails().getBusinessLicense());
			seller.setGstNumber(dto.getSellerDetails().getGstNumber());
			seller.setShopAddress(dto.getSellerDetails().getShopAddress());

			product.setSeller(seller);
		}
		return product;
	}

	// SellerDetails <-> SellerDetailsDto
	public static SellerDetailsDto toSellerDetailsDto(User seller) {
		if (seller == null)
			return null;
		SellerDetailsDto dto = new SellerDetailsDto();
		dto.setShopName(seller.getShopName());
		dto.setBusinessLicense(seller.getBusinessLicense());
		dto.setGstNumber(seller.getGstNumber());
		dto.setShopAddress(seller.getShopAddress());
		return dto;
	}

	public static User toSellerDetails(SellerDetailsDto dto) {
		if (dto == null)
			return null;
		User seller = new User();
		seller.setShopName(dto.getShopName());
		seller.setBusinessLicense(dto.getBusinessLicense());
		seller.setGstNumber(dto.getGstNumber());
		seller.setShopAddress(dto.getShopAddress());
		return seller;
	}

	// Cart <-> CartDto
	public static CartDto toCartDto(Cart cart) {
		if (cart == null)
			return null;
		CartDto dto = new CartDto();
		dto.setCartId(cart.getId());
		dto.setTotalAmount(cart.getTotalAmount());
		dto.setBasicUserDto(toBasicUserDto(cart.getUser()));
		dto.setItems(toCartItemDtoList(cart.getItems()));
		return dto;
	}

	public static Cart toCart(CartDto dto) {
		if (dto == null)
			return null;
		Cart cart = new Cart();
		cart.setId(dto.getCartId());
		cart.setUser(toBasicUser(dto.getBasicUserDto()));
		// cart.setTotalAmount(dto.getTotalAmount() != null ? dto.getTotalAmount() :
		// BigDecimal.ZERO);
		cart.setItems(toCartItemList(dto.getItems(), cart));
		cart.updateTotalAmount();
		return cart;
	}

	// CartItem <-> CartItemDto
	public static CartItemDto toCartItemDto(CartItem item) {
		if (item == null)
			return null;
		CartItemDto dto = new CartItemDto();
		dto.setItemId(item.getId());
		dto.setUnitPrice(item.getUnitPrice());
		dto.setQuantity(item.getQuantity());
		dto.setProduct(toProductDto(item.getProduct()));
		return dto;
	}

	public static CartItem toCartItem(CartItemDto dto, Cart cart) {
		if (dto == null)
			return null;
		CartItem item = new CartItem();
		item.setId(dto.getItemId());
		item.setUnitPrice(dto.getUnitPrice());
		item.setQuantity(dto.getQuantity());
		item.setCart(cart);
		// calculating total price
		if (dto.getUnitPrice() != null && dto.getQuantity() != null) {
			BigDecimal total = dto.getUnitPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
			item.setTotalPrice(total);
		}
		item.setProduct(toProduct(dto.getProduct()));

		return item;
	}

	// Order <-> OrderDto
	public static OrderDto toOrderDto(Order order) {
		if (order == null)
			return null;
		OrderDto dto = new OrderDto();
		dto.setId(order.getId());
		dto.setOrderDate(order.getOrderDate());
		dto.setStatus(order.getStatus());
		dto.setTotalPrice(order.getTotalPrice());
		dto.setBasicUserDto(toBasicUserDto(order.getUser()));
		dto.setPayment(toPaymentDto(order.getPayment()));
		dto.setOrderItems(toOrderItemDtoList(order.getOrderItems()));

		return dto;
	}

	public static Order toOrder(OrderDto dto) {
		if (dto == null)
			return null;
		Order order = new Order();
		order.setId(dto.getId());
		order.setOrderDate(dto.getOrderDate());
		order.setStatus(dto.getStatus());
		order.setTotalPrice(dto.getTotalPrice());
		order.setUser(toBasicUser(dto.getBasicUserDto()));
		order.setPayment(toPayment(dto.getPayment()));
		order.setOrderItems(toOrderItemList(dto.getOrderItems(), order));
		return order;
	}

	// OrderItem <-> OrderItemDto
	public static OrderItemDto toOrderItemDto(OrderItem item) {
		if (item == null)
			return null;
		OrderItemDto dto = new OrderItemDto();
		dto.setProductId(item.getProduct().getId());
		dto.setQuantity(item.getQuantity());
		dto.setPrice(item.getPrice());
		dto.setProductName(item.getProduct().getProductName());
		return dto;
	}

	public static OrderItem toOrderItem(OrderItemDto dto, Order order) {
		if (dto == null)
			return null;
		OrderItem item = new OrderItem();
		item.setOrder(order);
		item.setQuantity(dto.getQuantity());
		item.setPrice(dto.getPrice());
		if (dto.getProductId() != null) {
			Product product = new Product();
			product.setId(dto.getProductId());
			product.setProductName(dto.getProductName());
			item.setProduct(product);
		}
		return item;
	}

	public static PaymentDto toPaymentDto(Payment payment) {
		if (payment == null)
			return null;

		PaymentDto dto = new PaymentDto();
		dto.setId(payment.getId());
		dto.setAmount(payment.getAmount());
		dto.setPaymentMethod(payment.getPaymentMethod());
		dto.setPaymentStatus(payment.getPaymentStatus());

		return dto;
	}

	public static Payment toPayment(PaymentDto dto) {
		if (dto == null)
			return null;

		Payment payment = new Payment();
		payment.setId(dto.getId());
		payment.setAmount(dto.getAmount());
		payment.setPaymentMethod(dto.getPaymentMethod());
		payment.setPaymentStatus(dto.getPaymentStatus());

		return payment;
	}
	
	public static  ReviewDto convertToDto(Review review) {
	    ReviewDto dto = new ReviewDto();
	    dto.setId(review.getId());
	    dto.setComment(review.getContent());
	    dto.setRating(review.getRating());
	    dto.setProductId(review.getProduct().getId());
	    dto.setUserId(review.getUser().getId());
	    dto.setUserName(review.getUser().getName());
	    dto.setCreatedAt(review.getReviewDate());
	    return dto;
	}


	// helpers

	public static List<OrderDto> toOrderDtoList(List<Order> list) {
		if (list == null)
			return null;
		List<OrderDto> dtoList = new ArrayList();
		for (Order order : list) {
			dtoList.add(toOrderDto(order));
		}
		return dtoList;
	}

	public static List<Order> toOrderList(List<OrderDto> list) {
		if (list == null)
			return null;
		List<Order> entityList = new ArrayList<>();
		for (OrderDto dto : list) {
			entityList.add(toOrder(dto));
		}
		return entityList;
	}

	public static List<OrderItemDto> toOrderItemDtoList(List<OrderItem> list) {
		if (list == null)
			return null;
		List<OrderItemDto> dtoList = new ArrayList<>();
		for (OrderItem item : list) {
			dtoList.add(toOrderItemDto(item));
		}
		return dtoList;
	}

	public static List<OrderItem> toOrderItemList(List<OrderItemDto> list, Order order) {
		if (list == null)
			return null;
		List<OrderItem> entityList = new ArrayList<>();
		for (OrderItemDto dto : list) {
			entityList.add(toOrderItem(dto, order));
		}
		return entityList;
	}

	public static List<CartItemDto> toCartItemDtoList(List<CartItem> list) {
		if (list == null)
			return null;
		List<CartItemDto> dtoList = new ArrayList<>();
		for (CartItem item : list) {
			dtoList.add(toCartItemDto(item));
		}
		return dtoList;
	}

	public static List<CartItem> toCartItemList(List<CartItemDto> list, Cart cart) {
		if (list == null)
			return null;
		List<CartItem> entityList = new ArrayList<>();
		for (CartItemDto dto : list) {
			entityList.add(toCartItem(dto, cart));
		}
		return entityList;
	}

}
