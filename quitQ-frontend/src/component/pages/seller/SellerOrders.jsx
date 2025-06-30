import React, { useEffect, useState } from "react";

import ApiService from "../../../service/ApiService";

const SellerOrders = () => {
  const [orders, setOrders] = useState([]);
  const [summary, setSummary] = useState({});
  const [filterStatus, setFilterStatus] = useState("");

  useEffect(() => {
    getOrders();
    getSummary();
  }, []);

  const getOrders = async () => {
    try {
      const res = await ApiService.getSellerOrders();
      setOrders(res.orderList || []);
    } catch (err) {
      console.error("Failed to fetch orders", err);
    }
  };

  const getSummary = async () => {
    try {
      const res = await ApiService.getSellerOrderSummary();
      setSummary(res.orderSummary || {});
    } catch (err) {
      console.error("Failed to fetch summary", err);
    }
  };

  const updateStatus = async (orderId, status) => {
    try {
      await ApiService.updateOrderStatus(orderId, status);
      alert(`Order #${orderId} updated to ${status}`);
      getOrders();
      getSummary();
    } catch (err) {
      console.error("Error updating status", err);
      alert("Something went wrong.");
    }
  };

  const statusBadgeClass = (status) => {
    const classes = {
      PENDING: "badge bg-warning text-dark",
      PROCESSING: "badge bg-info text-dark",
      CONFIRMED: "badge bg-primary",
      SHIPPED: "badge bg-secondary",
      DELIVERED: "badge bg-success",
      CANCELLED: "badge bg-danger",
    };
    return classes[status] || "badge bg-light text-dark";
  };

  const shownOrders = filterStatus
    ? orders.filter((order) => order.status === filterStatus)
    : orders;

  return (
    <div className="container mt-4">
      <h2 className="mb-4">📦 Seller Orders</h2>

      {/* Summary */}
      <div className="row my-4">
        {Object.entries(summary).map(([key, val]) => (
          <div key={key} className="col-md-2 col-6 mb-2">
            <div className="card bg-primary text-white text-center">
              <div className="card-body p-2">
                <div className="text-uppercase small">{key}</div>
                <div className="fw-bold fs-5">{val}</div>
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* Filter Dropdown */}
      <div className="mb-3 d-flex justify-content-end">
        <div className="col-md-3">
          <select
            className="form-select"
            value={filterStatus}
            onChange={(e) => setFilterStatus(e.target.value)}
          >
            <option value="">All Statuses</option>
            <option value="PENDING">PENDING</option>
            <option value="PROCESSING">PROCESSING</option>
            <option value="SHIPPED">SHIPPED</option>
            <option value="DELIVERED">DELIVERED</option>
            <option value="CANCELLED">CANCELLED</option>
          </select>
        </div>
      </div>

      {/* Orders List */}
      {shownOrders.map((order) => (
        <div key={order.id} className="card mb-4 shadow-sm">
          <div className="card-header bg-white d-flex justify-content-between align-items-center">
            <div>
              <h5 className="mb-0">Order #{order.id}</h5>
              <small className="text-muted">
                Date: {new Date(order.orderDate).toLocaleString()}
              </small>
            </div>
            <div className="d-flex align-items-center gap-2">
              <span className={statusBadgeClass(order.status)}>
                {order.status}
              </span>
              <select
                className="form-select form-select-sm"
                value={order.status}
                onChange={(e) => updateStatus(order.id, e.target.value)}
              >
                <option value="PROCESSING">PROCESSING</option>
                <option value="SHIPPED">SHIPPED</option>
              </select>
            </div>
          </div>

          <div className="card-body">
            <h6 className="mb-2">Customer</h6>
            <p className="mb-1">
              <strong>{order.basicUserDto.name}</strong>
            </p>
            <p className="mb-1">{order.basicUserDto.email}</p>
            <p className="mb-3">{order.basicUserDto.phoneNumber}</p>

            <h6>Items</h6>
            <ul className="list-group mb-3">
              {order.orderItems.map((item, idx) => (
                <li
                  key={idx}
                  className="list-group-item d-flex justify-content-between"
                >
                  <span>
                    {item.productName} x {item.quantity}
                  </span>
                  <span>₹{item.price}</span>
                </li>
              ))}
            </ul>

            <h6>Payment</h6>
            <p className="mb-1">Method: {order.payment.paymentMethod}</p>
            <p className="mb-1">Amount: ₹{order.payment.amount.toFixed(2)}</p>
            <p>
              Status:{" "}
              <span className="badge bg-success">
                {order.payment.paymentStatus}
              </span>
            </p>
          </div>
        </div>
      ))}
    </div>
  );
};

export default SellerOrders;
