package com.hexaware.quitQ_backend_1.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hexaware.quitQ_backend_1.entities.CartItem;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int status;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    // Auth info
    private String token;
    private String role;
    private String expirationTime;

  
    private Long userId;
    private Integer count;
    private Object data;


    // Single objects
    private UserDto user;
    private AddressDto address;
    private CategoryDto category;
    private ProductDto product;
    private OrderDto order;
    private OrderItemDto orderItem;
    private CartDto cart;
    private CartItemDto cartItem;
    private SellerDetailsDto sellerDetails;
    private OrderSummaryDto orderSummary;
    private UserUpdateDTO userUpdateDTO;
    private SellerUpdateDTO sellerUpdateDTO; 
    private ReviewDto reviewDto;

    // Lists
    private List<UserDto> userList;
    private List<AddressDto> addressList;
    private List<CategoryDto> categoryList;
    private List<ProductDto> productList;
    private List<OrderDto> orderList;
    private List<OrderItemDto> orderItemList;
    private List<CartItem> cartItemList;
    private List<CartItemDto> cartItemDtoList;
    private List<SellerDetailsDto> sellerDetailsDtoList;
    private List<ReviewDto> reviewDtosList;

    // Constructors
    public Response() {
    }

    public Response(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}


	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

	public OrderDto getOrder() {
		return order;
	}

	public void setOrder(OrderDto order) {
		this.order = order;
	}

	public OrderItemDto getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItemDto orderItem) {
		this.orderItem = orderItem;
	}

	public CartDto getCart() {
		return cart;
	}

	public void setCart(CartDto cart) {
		this.cart = cart;
	}

	public CartItemDto getCartItem() {
		return cartItem;
	}

	public void setCartItem(CartItemDto cartItem) {
		this.cartItem = cartItem;
	}

	public SellerDetailsDto getSellerDetails() {
		return sellerDetails;
	}

	public void setSellerDetails(SellerDetailsDto sellerDetails) {
		this.sellerDetails = sellerDetails;
	}

	public List<UserDto> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDto> userList) {
		this.userList = userList;
	}

	public List<AddressDto> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<AddressDto> addressList) {
		this.addressList = addressList;
	}

	public List<CategoryDto> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryDto> categoryList) {
		this.categoryList = categoryList;
	}

	public List<ProductDto> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDto> productList) {
		this.productList = productList;
	}

	public List<OrderDto> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderDto> orderList) {
		this.orderList = orderList;
	}

	public List<OrderItemDto> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItemDto> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public List<CartItem> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(List<CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}

	public List<CartItemDto> getCartItemDtoList() {
		return cartItemDtoList;
	}

	public void setCartItemDtoList(List<CartItemDto> cartItemDtoList) {
		this.cartItemDtoList = cartItemDtoList;
	}

	public List<SellerDetailsDto> getSellerDetailsDtoList() {
		return sellerDetailsDtoList;
	}

	public void setSellerDetailsDtoList(List<SellerDetailsDto> sellerDetailsDtoList) {
		this.sellerDetailsDtoList = sellerDetailsDtoList;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public OrderSummaryDto getOrderSummary() {
		return orderSummary;
	}

	public void setOrderSummary(OrderSummaryDto orderSummary) {
		this.orderSummary = orderSummary;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public UserUpdateDTO getUserUpdateDTO() {
		return userUpdateDTO;
	}

	public void setUserUpdateDTO(UserUpdateDTO userUpdateDTO) {
		this.userUpdateDTO = userUpdateDTO;
	}

	public SellerUpdateDTO getSellerUpdateDTO() {
		return sellerUpdateDTO;
	}

	public void setSellerUpdateDTO(SellerUpdateDTO sellerUpdateDTO) {
		this.sellerUpdateDTO = sellerUpdateDTO;
	}

	public ReviewDto getReviewDto() {
		return reviewDto;
	}

	public void setReviewDto(ReviewDto reviewDto) {
		this.reviewDto = reviewDto;
	}

	public List<ReviewDto> getReviewDtosList() {
		return reviewDtosList;
	}

	public void setReviewDtosList(List<ReviewDto> reviewDtosList) {
		this.reviewDtosList = reviewDtosList;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

    // Getters and Setters

    
}
