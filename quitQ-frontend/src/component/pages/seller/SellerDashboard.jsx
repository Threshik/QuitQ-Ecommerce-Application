import React, { useEffect, useState } from "react";

import ApiService from "../../../service/ApiService";
import Spinner from "../../common/Spinner";

const SellerDashboard = () => {
  const [stats, setStats] = useState({
    totalProducts: 0,
    totalOrders: 0,
    confirmedOrders: 0,
    cancelledOrders: 0,
    totalRevenue: 0,
  });

  const [recentOrders, setRecentOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        const productCountData = await ApiService.getSellerProductCount();
        const sellerOrdersData = await ApiService.getSellerOrders();

        const orderList = sellerOrdersData.orderList || [];

        const totalOrders = orderList.length;
        const confirmedOrders = orderList.filter(
          (order) => order.status === "SHIPPED"
        ).length;
        const cancelledOrders = orderList.filter(
          (order) => order.status === "CANCELLED"
        ).length;

        const completedOrders = orderList.filter(
          (order) => order.status === "SHIPPED" || order.status === "DELIVERED"
        );

        const totalRevenue = completedOrders.reduce((sum, order) => {
          const orderRevenue = order.orderItems.reduce(
            (itemSum, item) => itemSum + item.price * item.quantity,
            0
          );
          return sum + orderRevenue;
        }, 0);

        console.log("Total Revenue:", totalRevenue);

        setStats({
          totalProducts: productCountData?.count || 0,
          totalOrders,
          confirmedOrders,
          cancelledOrders,
          totalRevenue,
        });

        setRecentOrders(orderList.slice(0, 5));
      } catch (error) {
        console.error("Error fetching dashboard data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchDashboardData();
  }, []);

  if (loading) {
    return <Spinner />;
  }

  return (
    <div className="container mt-5">
      <h2 className="mb-4 text-center fw-bold">Seller Dashboard</h2>

      {/* Stats Cards */}
      <div className="row row-cols-1 row-cols-sm-2 row-cols-md-5 g-4 mb-4">
        <div className="col">
          <div className="card h-100 text-white bg-primary shadow-sm">
            <div className="card-body text-center">
              <h6 className="card-title mb-2">
                <i className="bi bi-box-seam me-2"></i> Total Products
              </h6>
              <h3 className="card-text">{stats.totalProducts}</h3>
            </div>
          </div>
        </div>

        <div className="col">
          <div
            className="card h-100 text-white"
            style={{ backgroundColor: "#007bff" }}
          >
            <div className="card-body text-center">
              <h6 className="card-title mb-2">
                <i className="bi bi-bag-check me-2"></i> Total Orders
              </h6>
              <h3 className="card-text">{stats.totalOrders}</h3>
            </div>
          </div>
        </div>

        <div className="col">
          <div
            className="card h-100 text-white"
            style={{ backgroundColor: "#3399ff" }}
          >
            <div className="card-body text-center">
              <h6 className="card-title mb-2">
                <i className="bi bi-truck me-2"></i> Delivered
              </h6>
              <h3 className="card-text">{stats.confirmedOrders}</h3>
            </div>
          </div>
        </div>

        <div className="col">
          <div
            className="card h-100 text-white"
            style={{ backgroundColor: "#2980b9" }}
          >
            <div className="card-body text-center">
              <h6 className="card-title mb-2">
                <i className="bi bi-x-octagon me-2"></i> Cancelled
              </h6>
              <h3 className="card-text">{stats.cancelledOrders}</h3>
            </div>
          </div>
        </div>

        <div className="col">
          <div
            className="card h-100 text-white"
            style={{ backgroundColor: "#154360" }}
          >
            <div className="card-body text-center">
              <h6 className="card-title mb-2">
                <i className="bi bi-currency-rupee me-2"></i> Total Revenue
              </h6>
              <h3 className="card-text">₹{stats.totalRevenue.toFixed(2)}</h3>
            </div>
          </div>
        </div>
      </div>

      {/* Recent Orders Table */}
      <div className="card shadow-sm border-0">
        <div className="card-header  text fw-semibold">Recent Orders</div>
        <div className="card-body p-0">
          <div className="table-responsive">
            <table className="table mb-0 table-striped">
              <thead className="table-light">
                <tr>
                  <th>Order ID</th>
                  <th>Customer</th>
                  <th>Amount</th>
                  <th>Status</th>
                  <th>Date</th>
                </tr>
              </thead>
              <tbody>
                {recentOrders.length > 0 ? (
                  recentOrders.map((order) => (
                    <tr key={order.id}>
                      <td>{order.id}</td>
                      <td>{order.basicUserDto?.name || "N/A"}</td>
                      <td>₹{order.totalPrice.toFixed(2)}</td>
                      <td>{order.status}</td>
                      <td>{new Date(order.orderDate).toLocaleDateString()}</td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="5" className="text-center">
                      No orders available
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SellerDashboard;
