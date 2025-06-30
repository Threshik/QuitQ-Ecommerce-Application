import axios from "axios";

export default class ApiService {
  static BASE_URL = "http://localhost:8080";

  static getHeader(contentType = "application/json") {
    const token = localStorage.getItem("token");
    const headers = {
      Authorization: `Bearer ${token}`,
    };
    if (contentType) {
      headers["Content-Type"] = contentType;
    }
    return headers;
  }

  /**Auth API **/
  static async registerUser(registrationData) {
    try {
      const response = await axios.post(
        `${this.BASE_URL}/auth/register`,
        registrationData
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }
  static async loginUser(loginData) {
    try {
      const response = await axios.post(
        `${this.BASE_URL}/auth/login`,
        loginData
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  /** Users API **/
  static async getAllUsers() {
    try {
      const response = await axios.get(`${this.BASE_URL}/api/users/get-all`, {
        headers: this.getHeader(),
      });
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }
  static async getUserById(userId) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/users/getUserById/${userId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }
  static async updateUser(id, userData) {
    try {
      const response = await axios.put(
        `${this.BASE_URL}/api/users/update/${id}`,
        userData,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async updateSeller(id, sellerData) {
    try {
      const response = await axios.put(
        `${this.BASE_URL}/api/users/update-seller/${id}`,
        sellerData,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  /*Admin API */

  static async getUserCount() {
    try {
      const response = await axios.get(`${this.BASE_URL}/admin/userCount`, {
        headers: this.getHeader(),
      });
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getProductCount() {
    try {
      const response = await axios.get(`${this.BASE_URL}/admin/productCount`, {
        headers: this.getHeader(),
      });
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getCategoryCount() {
    try {
      const response = await axios.get(`${this.BASE_URL}/admin/categoryCount`, {
        headers: this.getHeader(),
      });
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getOrderCount() {
    try {
      const response = await axios.get(`${this.BASE_URL}/admin/orderCount`, {
        headers: this.getHeader(),
      });
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  /* Product API */
  static async createProduct(
    categoryId,
    image,
    name,
    description,
    price,
    brand,
    inventory
  ) {
    try {
      const formData = new FormData();
      formData.append("categoryId", categoryId);
      formData.append("image", image);
      formData.append("name", name);
      formData.append("description", description);
      formData.append("price", price);
      formData.append("brand", brand);
      formData.append("inventory", inventory);

      const response = await axios.post(
        `${this.BASE_URL}/product/create`,
        formData,
        {
          headers: this.getHeader(null),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async updateProduct(
    productId,
    categoryId,
    image,
    name,
    description,
    price,
    brand,
    inventory
  ) {
    try {
      const formData = new FormData();
      formData.append("categoryId", categoryId);
      formData.append("image", image); // backend expects 'image'
      formData.append("name", name);
      formData.append("description", description);
      formData.append("price", price);
      formData.append("brand", brand);
      formData.append("inventory", inventory);

      const response = await axios.put(
        `${this.BASE_URL}/product/update/${productId}`,
        formData,
        {
          headers: this.getHeader(null),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async deleteProduct(productId) {
    try {
      const response = await axios.delete(
        `${this.BASE_URL}/product/deleteProduct/${productId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getProductById(productId) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/product/getProductById/${productId}`
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getAllProducts() {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/product/getAllProducts`
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getProductsByCategory(category) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/product/getProductsByCategory/${category}`
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }
  static async getSellerProductCount() {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/product/seller/productCount`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getSellerProducts() {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/product/seller/products`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getAllSellersWithProducts() {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/product/admin/sellers-with-products`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  /* Category API */
  static async createCategory(categoryDto) {
    try {
      const response = await axios.post(
        `${this.BASE_URL}/api/category/create`,
        categoryDto,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async updateCategory(categoryId, categoryDto) {
    try {
      const response = await axios.put(
        `${this.BASE_URL}/api/category/update/${categoryId}`,
        categoryDto,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getAllCategories() {
    try {
      const response = await axios.get(`${this.BASE_URL}/api/category/get-all`);
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getCategoryById(categoryId) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/category/getCategoryById/${categoryId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async deleteCategory(categoryId) {
    try {
      const response = await axios.delete(
        `${this.BASE_URL}/api/category/deleteCategory/${categoryId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  /** Address API **/
  static async saveOrUpdateAddress(addressDto) {
    try {
      const response = await axios.post(
        `${this.BASE_URL}/api/address/save`,
        addressDto,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getAddressById(id) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/address/getAddressById/${id}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async deleteAddress(id) {
    try {
      const response = await axios.delete(
        `${this.BASE_URL}/api/address/deleteAddress/${id}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  /* Cart API */
  static async getCartByUserId(userId) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/cart/getCartByUserId/${userId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data.cart;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async clearCart(userId) {
    try {
      const response = await axios.delete(
        `${this.BASE_URL}/api/cart/clear/${userId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getTotalPrice(cartId) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/cart/getTotalPrice/${cartId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async initializeNewCart() {
    try {
      const response = await axios.post(
        `${this.BASE_URL}/api/cart/initialize`,
        {},
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }
  /** Cart Item API **/
  static async addItemToCart(cartId, productId, quantity) {
    try {
      const response = await axios.post(
        `${this.BASE_URL}/api/cart-items/addToCart/${cartId}/${productId}/${quantity}`,
        null,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async removeItemFromCart(cartId, productId) {
    try {
      const response = await axios.delete(
        `${this.BASE_URL}/api/cart-items/removeItemFromCart/${cartId}/${productId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async updateItemQuantity(cartId, productId, quantity) {
    try {
      const response = await axios.put(
        `${this.BASE_URL}/api/cart-items/updateItemQuantity/${cartId}/${productId}/${quantity}`,
        null,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getCartItem(cartId, productId) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/cart-items/getCartItem/${cartId}/${productId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async adminGetCartItem(cartId, productId) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/cart-items/admin/getCartItem/${cartId}/${productId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getAllCartItems(cartId) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/cart-items/getAllCartItems/${cartId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  /** Order API **/
  static async placeOrder(paymentDto) {
    try {
      const response = await axios.post(
        `${this.BASE_URL}/api/orders/placeOrder`,
        paymentDto,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getOrderById(orderId) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/orders/getOrder/${orderId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }
  static async getAllOrders() {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/orders/getAllOrders`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }
  static async getUserOrdersByAdmin(userId) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/orders/getUserOrders/${userId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async cancelOrder(orderId, userId) {
    try {
      const response = await axios.delete(
        `${this.BASE_URL}/api/orders/cancelOrder/${orderId}/${userId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async updateOrderStatus(orderId, status) {
    try {
      const response = await axios.put(
        `${this.BASE_URL}/api/orders/updateOrderStatus/${orderId}/${status}`,
        {},
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getMyOrders() {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/orders/getUserOrders`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getSellerOrderSummary() {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/orders/seller/orderSummary`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getSellerOrders() {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/orders/seller/orders`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  /** Order Item API **/

  static async deleteOrderItem(orderItemId) {
    try {
      const response = await axios.delete(
        `${this.BASE_URL}/api/order-items/deleteOrderItem/${orderItemId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getAllOrderItems() {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/order-items/getAllOrderItems`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getOrderItemsByOrderId(orderId) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/order-items/getOrderItemsByOrderId/${orderId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  /** review API */
  static async createReview(reviewData) {
    try {
      const response = await axios.post(
        `${this.BASE_URL}/api/reviews/save`,
        reviewData,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  static async getReviewsByProductId(productId) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/api/reviews/product/${productId}`,
        {
          headers: this.getHeader(),
        }
      );
      return response.data;
    } catch (error) {
      throw error.response ? error.response.data : error;
    }
  }

  /* Authentication API */
  static logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
  }
  static isAuthenticated() {
    return !!localStorage.getItem("token");
  }
  static getUserRole() {
    return localStorage.getItem("role");
  }
  static isAdmin() {
    return this.getUserRole() === "ADMIN";
  }
  static isSeller() {
    return this.getUserRole() === "SELLER";
  }
  static isUser() {
    return this.getUserRole() === "USER";
  }
}
