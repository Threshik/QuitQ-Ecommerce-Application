import { Link, useNavigate } from "react-router-dom";
import React, { useState } from "react";

import ApiService from "../../service/ApiService";

const Register = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    gender: "",
    email: "",
    phoneNumber: "",
    password: "",
    role: "user",
    // seller-specific
    shopName: "",
    businessLicense: "",
    gstNumber: "",
    shopAddress: "",
  });

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    try {
      await ApiService.registerUser(formData);
      setSuccess("Registration successful! Redirecting to login...");
      setTimeout(() => navigate("/login"), 1500);
    } catch (err) {
      console.error("Registration failed", err);
      setError("Registration failed. Try again.");
    }
  };

  const isSeller = formData.role === "SELLER";

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
        <h3 className="text-center mb-4 text-primary">Register</h3>
        {error && <div className="alert alert-danger">{error}</div>}
        {success && <div className="alert alert-success">{success}</div>}

        <form onSubmit={handleSubmit}>
          {/* Role Selection */}
          <div className="mb-3">
            <label className="form-label">Register as</label>
            <select
              name="role"
              className="form-select"
              value={formData.role}
              onChange={handleChange}
              required
            >
              <option value="">Select Role</option>
              <option value="USER">User</option>
              <option value="ADMIN">Admin</option>
              <option value="SELLER">Seller</option>
            </select>
          </div>

          {/* Common Fields */}
          <div className="mb-3">
            <label className="form-label">Full Name</label>
            <input
              type="text"
              name="name"
              className="form-control"
              value={formData.name}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Gender</label>
            <select
              name="gender"
              className="form-select"
              value={formData.gender}
              onChange={handleChange}
              required
            >
              <option value="">Select Gender</option>
              <option value="MALE">Male</option>
              <option value="FEMALE">Female</option>
              <option value="OTHER">Other</option>
            </select>
          </div>

          <div className="mb-3">
            <label className="form-label">Email</label>
            <input
              type="email"
              name="email"
              className="form-control"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Phone Number</label>
            <input
              type="tel"
              name="phoneNumber"
              className="form-control"
              value={formData.phoneNumber}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Password</label>
            <input
              type="password"
              name="password"
              className="form-control"
              value={formData.password}
              onChange={handleChange}
              required
              autoComplete="new-password"
            />
          </div>

          {/* Seller-Specific Fields */}
          {isSeller && (
            <>
              <div className="mb-3">
                <label className="form-label">Shop Name</label>
                <input
                  type="text"
                  name="shopName"
                  className="form-control"
                  value={formData.shopName}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Business License</label>
                <input
                  type="text"
                  name="businessLicense"
                  className="form-control"
                  value={formData.businessLicense}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="mb-3">
                <label className="form-label">GST Number</label>
                <input
                  type="text"
                  name="gstNumber"
                  className="form-control"
                  value={formData.gstNumber}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Shop Address</label>
                <textarea
                  name="shopAddress"
                  className="form-control"
                  rows="2"
                  value={formData.shopAddress}
                  onChange={handleChange}
                  required
                />
              </div>
            </>
          )}

          <button type="submit" className="btn btn-primary w-100">
            Register
          </button>
          {/* Login Link */}
          <div className="text-center mt-3">
            <small>
              Already a user? <Link to="/login">Login</Link>
            </small>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Register;
