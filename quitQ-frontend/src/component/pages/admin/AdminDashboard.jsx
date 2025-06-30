import {
  FaBoxOpen,
  FaCogs,
  FaShoppingCart,
  FaTags,
  FaUsers,
} from "react-icons/fa";
import React, { useEffect, useState } from "react";

import ApiService from "../../../service/ApiService";
import Spinner from "../../common/Spinner";
import { useNavigate } from "react-router-dom";

const AdminDashboard = () => {
  const [counts, setCounts] = useState({
    users: 0,
    products: 0,
    categories: 0,
    orders: 0,
  });

  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCounts = async () => {
      try {
        const userRes = await ApiService.getUserCount();
        const productRes = await ApiService.getProductCount();
        const categoryRes = await ApiService.getCategoryCount();
        const orderRes = await ApiService.getOrderCount();

        setCounts({
          users: userRes.count,
          products: productRes.count,
          categories: categoryRes.count,
          orders: orderRes.count,
        });
        setLoading(false);
      } catch (error) {
        console.error("Error fetching dashboard data:", error);
        setLoading(false);
      }
    };

    fetchCounts();
  }, []);

  if (loading) {
    return <Spinner />;
  }

  return (
    <div className="container mt-5">
      <h2 className="mb-4 text-center fw-bold">Admin Dashboard</h2>

      {/* Stat Cards */}
      <div className="row g-4 mb-5">
        <DashboardCard
          bg="#007bff"
          title="Users"
          icon={<FaUsers size={28} />}
          count={counts.users}
        />
        <DashboardCard
          bg="#3399ff"
          title="Products"
          icon={<FaBoxOpen size={28} />}
          count={counts.products}
        />
        <DashboardCard
          bg="#0a58ca"
          title="Categories"
          icon={<FaTags size={28} />}
          count={counts.categories}
        />
        <DashboardCard
          bg="#084298"
          title="Orders"
          icon={<FaShoppingCart size={28} />}
          count={counts.orders}
        />
      </div>

      {/* Management Cards */}
      <h4 className="mb-3 fw-semibold">Manage</h4>
      <div className="row g-4">
        <ManagementCard
          title="Manage Users"
          route="/admin/manage-users"
          icon={<FaUsers />}
        />
        <ManagementCard
          title="Manage Products"
          route="/admin/manage-products"
          icon={<FaBoxOpen />}
        />
        <ManagementCard
          title="Manage Categories"
          route="/admin/manage-categories"
          icon={<FaTags />}
        />
        <ManagementCard
          title="Manage Orders"
          route="/admin/manage-orders"
          icon={<FaShoppingCart />}
        />
        <ManagementCard
          title="Manage Sellers"
          route="/admin/manage-sellers"
          icon={<FaCogs />}
        />
      </div>
    </div>
  );
};

// Reusable Count Card (with custom blue shades)
const DashboardCard = ({ bg, title, count, icon }) => (
  <div className="col-md-3">
    <div
      className="card text-white h-100 shadow-sm"
      style={{ backgroundColor: bg, borderRadius: "0.75rem" }}
    >
      <div className="card-body text-center">
        <div className="mb-2">{icon}</div>
        <h5 className="card-title">{title}</h5>
        <p className="card-text fs-3 fw-bold">{count}</p>
      </div>
    </div>
  </div>
);

// Reusable Management Navigation Card
const ManagementCard = ({ title, route, icon }) => {
  const navigate = useNavigate();
  return (
    <div className="col-md-4">
      <div
        className="card border-0 shadow-sm h-100 text-center p-4"
        style={{
          cursor: "pointer",
          borderRadius: "0.75rem",
        }}
        onClick={() => navigate(route)}
      >
        <div className="text-primary mb-3" style={{ fontSize: "2rem" }}>
          {icon}
        </div>
        <h5 className="card-title fw-semibold">{title}</h5>
      </div>
    </div>
  );
};

export default AdminDashboard;
