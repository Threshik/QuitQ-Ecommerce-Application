import React, { useEffect, useState } from "react";

import ApiService from "../../../service/ApiService";
import Spinner from "../../common/Spinner";
import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";

const Cart = () => {
  const [cart, setCart] = useState(null);
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const { auth } = useAuth();
  const navigate = useNavigate();

  const fetchCart = async () => {
    try {
      const userId = auth?.userId;
      if (!userId) throw new Error("User ID not found in localStorage");

      const cart = await ApiService.getCartByUserId(userId);
      if (!cart || !cart.cartId) throw new Error("Cart data not found");

      setCart(cart);

      const itemsResponse = await ApiService.getAllCartItems(cart.cartId);
      setItems(itemsResponse?.cartItemDtoList || []);
    } catch (error) {
      console.error("Error fetching cart:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCart();
  }, []);

  const handleRemove = async (itemId, productId) => {
    try {
      await ApiService.removeItemFromCart(cart.cartId, productId);
      setItems((prev) => prev.filter((item) => item.itemId !== itemId));
      const updatedCart = await ApiService.getCartByUserId(
        localStorage.getItem("userId")
      );
      setCart(updatedCart);
    } catch (error) {
      console.error("Error removing item:", error);
    }
  };

  const handleClearCart = async () => {
    const confirmClear = window.confirm(
      "Are you sure you want to clear the cart?"
    );
    if (!confirmClear) return;

    try {
      const userId = auth?.userId;
      await ApiService.clearCart(userId);
      setItems([]);
      setCart((prev) => ({ ...prev, totalAmount: 0 }));
      alert("Cart cleared successfully!");
    } catch (error) {
      console.error("Error clearing cart:", error);
      alert("Failed to clear cart. Please try again.");
    }
  };

  const handleCheckout = () => {
    alert("Proceeding to checkout...");
    navigate("/user/checkout");
  };

  const handleQuantityChange = async (itemId, newQty, productId) => {
    if (newQty < 1 || !productId || !cart?.cartId) return;

    try {
      await ApiService.updateItemQuantity(cart.cartId, productId, newQty);
      setItems((prev) =>
        prev.map((item) =>
          item.itemId === itemId ? { ...item, quantity: newQty } : item
        )
      );
      const updatedCart = await ApiService.getCartByUserId(userId);
      setCart(updatedCart);
    } catch (error) {
      console.error("Error updating quantity:", error);
    }
  };

  if (loading) return <Spinner />;

  return (
    <div className="container mt-5">
      <h2 className="mb-4 text-center">🛒 Your Shopping Cart</h2>

      {items.length === 0 ? (
        <div className="alert alert-info text-center">Your cart is empty.</div>
      ) : (
        <>
          <div className="row">
            {items.map((item) => (
              <div className="col-md-4 mb-4" key={item.itemId}>
                <div className="card h-100 shadow-sm">
                  <img
                    src={item.product.imageUrl}
                    alt={item.product.productName}
                    className="card-img-top p-3"
                    style={{
                      maxHeight: "180px",
                      objectFit: "contain",
                      borderBottom: "1px solid #eee",
                    }}
                  />
                  <div className="card-body d-flex flex-column">
                    <h5 className="card-title">{item.product?.productName}</h5>
                    <p className="card-text mb-1">
                      Price: ₹{item.unitPrice.toFixed(2)}
                    </p>
                    <div className="mb-2">
                      <label className="form-label me-2">Quantity:</label>
                      <input
                        type="number"
                        className="form-control w-50"
                        min="1"
                        value={item.quantity}
                        onChange={(e) =>
                          handleQuantityChange(
                            item.itemId,
                            parseInt(e.target.value),
                            item.product?.id
                          )
                        }
                      />
                    </div>
                    <p className="card-text">
                      Subtotal: ₹{(item.quantity * item.unitPrice).toFixed(2)}
                    </p>
                  </div>
                  <div className="card-footer text-end">
                    <button
                      className="btn btn-outline-danger btn-sm"
                      onClick={() => {
                        if (
                          window.confirm(
                            "Are you sure you want to remove this item?"
                          )
                        ) {
                          handleRemove(item.itemId, item.product.id);
                        }
                      }}
                    >
                      Remove
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>

          {/* Cart Summary and Actions */}
          <div className="row mt-4">
            <div className="col-md-6 offset-md-3">
              <div className="card border-primary shadow-sm">
                <div className="card-body text-center">
                  <h4 className="card-title mb-3">
                    Total Amount: ₹{cart.totalAmount.toFixed(2)}
                  </h4>
                  <div className="d-flex justify-content-center gap-3">
                    <button
                      className="btn btn-success px-4"
                      onClick={handleCheckout}
                    >
                      Checkout
                    </button>
                    <button
                      className="btn btn-outline-secondary px-4"
                      onClick={handleClearCart}
                    >
                      Clear Cart
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default Cart;
