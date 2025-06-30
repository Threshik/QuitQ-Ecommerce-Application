import React, { useEffect, useRef, useState } from "react";

import ApiService from "../../../service/ApiService";
import Spinner from "../../common/Spinner";
import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";

const Checkout = () => {
  const [user, setUser] = useState(null);
  const [paymentMethod, setPaymentMethod] = useState("COD");
  const navigate = useNavigate();
  const { auth } = useAuth();
  const redirected = useRef(false);
  useEffect(() => {
    const fetchUserAndCart = async () => {
      const userId = auth?.userId;
      if (!userId) {
        alert("Please log in to continue checkout.");
        navigate("/login");
        return;
      }
      try {
        const userResponse = await ApiService.getUserById(userId);
        const userData = userResponse.user;
        if (!userData.address || !userData.address.id) {
          if (!redirected.current) {
            redirected.current = true;
            alert("Please add a shipping address.");
            navigate("/user/address", { state: { from: "checkout" } });
          }
          return;
        }
        const cartData = await ApiService.getCartByUserId(userId);
        setUser({ ...userData, cart: cartData });
      } catch (err) {
        console.error("Error:", err);
        alert("Could not load user or cart data.");
      }
    };
    fetchUserAndCart();
  }, [auth, navigate]);

  const handlePlaceOrder = async () => {
    if (!user || !user.address) {
      alert("No shipping address selected.");
      return;
    }
    const paymentDto = {
      addressId: user.address.id,
      paymentMethod: paymentMethod,
      orderItems: user.cart.items.map((item) => ({
        productId: item.id,
        quantity: item.quantity,
      })),
    };
    try {
      await ApiService.placeOrder(paymentDto);
      alert("Order placed successfully!");
      navigate("/user/orders");
    } catch (err) {
      alert("Failed to place order: " + (err.message || "Unknown error"));
    }
  };

  const handleChangeAddress = () => {
    navigate("/user/address", { state: { from: "checkout" } });
  };

  if (!user) return <Spinner />;
  const address = user.address;
  const cartItems = user.cart.items || [];
  const total = user.cart.totalAmount || 0;
  return (
    <div className="container my-5">
      <h2 className="mb-4">Checkout</h2>
      {/* Shipping Address */}
      <div className="card mb-4">
        <div className="card-header d-flex justify-content-between align-items-center">
          <h5>Shipping Address</h5>
          <button
            className="btn btn-sm btn-outline-primary"
            onClick={handleChangeAddress}
          >
            Change
          </button>
        </div>
        <div className="card-body">
          <p>
            <strong>Type:</strong> {address.addressType}
          </p>
          <p>
            {address.street}, {address.city}
          </p>
          <p>
            {address.state} - {address.zipCode}
          </p>
          <p>{address.country}</p>
        </div>
      </div>
      {/* Order Summary */}
      <div className="card mb-4">
        <div className="card-header">
          <h5>Order Summary</h5>
        </div>
        <ul className="list-group list-group-flush">
          {cartItems.map((item, idx) => (
            <li
              key={idx}
              className="list-group-item d-flex justify-content-between"
            >
              <div>
                {item.product.productName}{" "}
                <small className="text-muted">x {item.quantity}</small>
              </div>
              <div>₹{item.unitPrice * item.quantity}</div>
            </li>
          ))}
        </ul>
        <div className="card-footer d-flex justify-content-between">
          <strong>Total</strong>
          <strong>₹{total}</strong>
        </div>
      </div>
      {/* Payment Method Selection */}
      <div className="mb-4">
        <label htmlFor="paymentMethod" className="form-label">
          Payment Method
        </label>
        <select
          id="paymentMethod"
          className="form-select"
          value={paymentMethod}
          onChange={(e) => setPaymentMethod(e.target.value)}
        >
          <option value="COD">Cash on Delivery</option>
          <option value="CARD">Card Payment</option>
          <option value="UPI">UPI Payment</option>
          <option value="NET_BANKING">Net Banking</option>
          <option value="WALLET">Wallet Payment</option>
          <option value="PAYLATER">Pay Later</option>
        </select>
      </div>
      {/* Place Order Button */}
      <button className="btn btn-success w-100" onClick={handlePlaceOrder}>
        Place Order
      </button>
    </div>
  );
};
export default Checkout;
