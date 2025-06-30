import { Link, useNavigate } from "react-router-dom";
import React, { useState } from "react";

import ApiService from "../../service/ApiService";
import { useAuth } from "../context/AuthContext";

const Login = () => {
  const [loginData, setLoginData] = useState({ email: "", password: "" });
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    if (!loginData.email || !loginData.password) {
      setError("Email and password are required");
      return;
    }

    if (loginData.password.length < 6) {
      setError("Password must be at least 6 characters long");
      return;
    }

    try {
      const data = await ApiService.loginUser(loginData);

      login(data.token, data.role, data.userId, data.user.name);

      setSuccess("Login successful! Redirecting...");

      if (data.role === "USER") {
        await ApiService.initializeNewCart();
      }

      const role = data.role ? data.role.toLowerCase() : "";

      setTimeout(() => {
        if (role === "ADMIN") {
          navigate("/admin/dashboard");
        } else if (role === "SELLER") {
          navigate("/seller/dashboard");
        } else {
          navigate("/home");
        }
      }, 1500);
    } catch (err) {
      console.error("Login failed", err);
      setError("Invalid email or password");
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div
        className="p-4 rounded shadow"
        style={{
          width: "100%",
          maxWidth: "450px",
          background: "#fff",
          maxHeight: "90vh",
          overflowY: "auto",
        }}
      >
        <h3 className="text-center mb-4 text-primary">Login</h3>

        {/* Success and Error Messages */}
        {success && (
          <div className="alert alert-success" role="alert">
            {success}
          </div>
        )}
        {error && (
          <div className="alert alert-danger" role="alert">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="email" className="form-label">
              Email
            </label>
            <div className="input-group">
              <span className="input-group-text">
                <i className="bi bi-envelope-fill"></i>
              </span>
              <input
                type="email"
                id="email"
                className="form-control"
                placeholder="Enter your email"
                value={loginData.email}
                onChange={(e) =>
                  setLoginData({ ...loginData, email: e.target.value })
                }
                required
              />
            </div>
          </div>

          <div className="mb-4">
            <label htmlFor="password" className="form-label">
              Password
            </label>
            <div className="input-group">
              <span className="input-group-text">
                <i className="bi bi-lock-fill"></i>
              </span>
              <input
                type="password"
                id="password"
                className="form-control"
                placeholder="Enter your password"
                value={loginData.password}
                onChange={(e) =>
                  setLoginData({ ...loginData, password: e.target.value })
                }
                required
                autoComplete="current-password"
              />
            </div>
          </div>

          <button type="submit" className="btn btn-primary w-100">
            Login
          </button>
        </form>

        <div className="text-center mt-3">
          <small>
            Don’t have an account? <Link to="/register">Register</Link>
          </small>
        </div>
      </div>
    </div>
  );
};

export default Login;
