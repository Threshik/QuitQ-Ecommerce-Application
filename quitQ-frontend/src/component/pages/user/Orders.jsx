import React, { useEffect, useState } from "react";

import ApiService from "../../../service/ApiService";
import Spinner from "../../common/Spinner";
import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";

const Orders = () => {
  const [orders, setOrders] = useState([]);
  const navigate = useNavigate();
  const { auth } = useAuth();

  useEffect(() => {
    const fetchOrders = async () => {
      const userId = auth?.userId;
      if (!userId) {
        alert("Please log in to view orders.");
        navigate("/login");
        return;
      }

      try {
        const response = await ApiService.getMyOrders(userId);
        setOrders(response.orderList);
      } catch (error) {
        console.error("Failed to fetch orders", error);
        alert("Error loading your orders.");
      }
    };

    fetchOrders();
  }, []);

  const handleCancelOrder = async (orderId) => {
    const userId = auth?.userId;
    if (!userId) return;

    const confirmCancel = window.confirm(
      "Are you sure you want to cancel this order?"
    );
    if (!confirmCancel) return;

    try {
      await ApiService.cancelOrder(orderId, userId);
      alert("Order canceled successfully.");

      const response = await ApiService.getMyOrders(userId);
      setOrders(response.orderList);
    } catch (error) {
      console.error("Failed to cancel order", error);
      alert("Unable to cancel order.");
    }
  };

  const renderTimeline = (status) => {
    const stages = ["CONFIRMED", "PACKED", "SHIPPED", "DELIVERED"];
    const currentIndex = stages.indexOf(status);

    return (
      <div className="d-flex justify-content-between align-items-center">
        {stages.map((stage, i) => (
          <div key={i} className="text-center flex-fill">
            <div
              className={`rounded-circle mx-auto mb-1 ${
                i <= currentIndex ? "bg-success" : "bg-secondary"
              }`}
              style={{ width: 20, height: 20 }}
            ></div>
            <small>{stage}</small>
          </div>
        ))}
      </div>
    );
  };

  return (
    <div className="container my-5">
      <h2 className="mb-4">My Orders</h2>
      {orders.length === 0 ? (
        <p>No orders found.</p>
      ) : (
        orders.map((order) => (
          <div className="card mb-4" key={order.id}>
            <div className="card-header d-flex justify-content-between align-items-center">
              <strong>Order </strong>
              <span
                className={`badge bg-${
                  order.status === "DELIVERED"
                    ? "success"
                    : order.status === "CANCELLED" ||
                      order.status === "CANCELED"
                    ? "danger"
                    : "primary"
                }`}
              >
                {order.status}
              </span>
            </div>
            <div className="card-body">
              <p>
                <strong>Date:</strong>{" "}
                {new Date(order.orderDate).toLocaleDateString()}
              </p>
              <p>
                <strong>Payment:</strong> {order.payment.paymentMethod}
              </p>
              <p>
                <strong>Total:</strong> ₹{order.totalPrice}
              </p>

              {renderTimeline(order.status)}

              <hr />
              {order.orderItems.map((item, index) => (
                <div key={index} className="d-flex justify-content-between">
                  <span>
                    {item.productName} x {item.quantity}
                  </span>
                  <span>₹{item.quantity * item.price}</span>
                </div>
              ))}

              <div className="mt-3 d-flex gap-2">
                {order.status !== "DELIVERED" &&
                  order.status !== "CANCELLED" &&
                  order.status !== "CANCELED" && (
                    <button
                      className="btn btn-outline-danger"
                      onClick={() => handleCancelOrder(order.id)}
                    >
                      Cancel Order
                    </button>
                  )}

                {(order.status === "CANCELLED" ||
                  order.status === "CANCELED") && (
                  <button className="btn btn-outline-danger" disabled>
                    Order Cancelled
                  </button>
                )}
              </div>
            </div>
          </div>
        ))
      )}
    </div>
  );
};

export default Orders;
