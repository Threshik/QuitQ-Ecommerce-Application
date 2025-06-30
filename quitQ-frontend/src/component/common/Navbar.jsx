import { Link, useNavigate } from "react-router-dom";
import React, { useState } from "react";

import { useAuth } from "../context/AuthContext";

const Navbar = ({ onSearch }) => {
  const [searchText, setSearchText] = useState("");
  const { auth, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  const handleSearch = (e) => {
    e.preventDefault();
    const trimmed = searchText.trim();

    if (trimmed === "") {
      navigate("/home");
    } else {
      navigate(`/home?search=${encodeURIComponent(trimmed)}`);
    }
  };

  const role = auth.role;
  const name = auth.name;

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
      <div className="container">
        <Link className="navbar-brand fw-bold text-secondary" to="/">
          QuitQ
        </Link>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <Link className="nav-link" to="/">
                Home
              </Link>
            </li>

            {role === "USER" && (
              <>
                <li className="nav-item">
                  <Link className="nav-link" to="/user/cart">
                    Cart
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/user/orders">
                    My Orders
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/user/profile">
                    Profile
                  </Link>
                </li>
              </>
            )}

            {role === "SELLER" && (
              <>
                <li className="nav-item">
                  <Link className="nav-link" to="/seller/dashboard">
                    Dashboard
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/seller/products">
                    Products
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/seller/orders">
                    Orders
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/seller/profile">
                    Profile
                  </Link>
                </li>
              </>
            )}

            {role === "ADMIN" && (
              <li className="nav-item">
                <Link className="nav-link" to="/admin/dashboard">
                  Admin Panel
                </Link>
              </li>
            )}
          </ul>

          {/*  Styled Search Bar with Icon */}
          <form
            className="d-flex align-items-center me-3"
            onSubmit={handleSearch}
          >
            <div className="input-group">
              <span className="input-group-text bg-white border-end-0">
                <i className="bi bi-search text-secondary"></i>
              </span>
              <input
                type="search"
                className="form-control border-start-0"
                placeholder="Search for Products"
                value={searchText}
                onChange={(e) => setSearchText(e.target.value)}
                style={{ minWidth: "400px" }}
              />
            </div>
          </form>

          {/*  Auth Controls */}
          <ul className="navbar-nav ms-auto align-items-center">
            {role ? (
              <>
                <li className="nav-item me-3">
                  <span className="nav-link text-secondary fw-semibold">
                    Hi, {name}
                  </span>
                </li>
                <li className="nav-item">
                  <button
                    className="btn btn-outline-danger"
                    onClick={handleLogout}
                  >
                    Logout
                  </button>
                </li>
              </>
            ) : (
              <>
                <li className="nav-item">
                  <Link className="btn btn-outline-secondary me-2" to="/login">
                    Login
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="btn btn-secondary" to="/register">
                    Register
                  </Link>
                </li>
              </>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
