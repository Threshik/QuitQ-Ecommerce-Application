import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

import ApiService from "../../../service/ApiService";
import { useAuth } from "../../context/AuthContext";

const Address = () => {
  const navigate = useNavigate();
  const { auth } = useAuth();
  const userId = auth?.userId;
  const location = useLocation();
  const from = location.state?.from || "profile";

  const [address, setAddress] = useState({
    addressType: "",
    street: "",
    city: "",
    state: "",
    zipCode: "",
    country: "",
  });

  useEffect(() => {
    const fetchAddress = async () => {
      if (!userId) {
        alert("Please log in to access address details.");
        navigate("/login");
        return;
      }

      try {
        const res = await ApiService.getUserById(userId);
        const userAddress = res.user?.address;
        if (userAddress) {
          setAddress(userAddress);
        }
      } catch (error) {
        console.error("Error fetching address:", error);
      }
    };

    fetchAddress();
  }, [userId, navigate]);

  const handleChange = (e) => {
    setAddress({ ...address, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await ApiService.saveOrUpdateAddress(address);
      alert(" Address saved successfully!");

      // Navigate based on source
      if (from === "checkout") {
        navigate("/user/checkout");
      } else {
        navigate("/user/profile");
      }
    } catch (error) {
      alert(" Failed to save address: " + (error?.message || "Unknown error"));
    }
  };

  return (
    <div className="container my-5">
      <h2 className="mb-4">Add / Update Shipping Address</h2>
      <form onSubmit={handleSubmit} className="card p-4">
        <div className="mb-3">
          <label className="form-label">Address Type</label>
          <select
            className="form-select"
            name="addressType"
            value={address.addressType}
            onChange={handleChange}
            required
          >
            <option value="">Select Type</option>
            <option value="HOME">Home</option>
            <option value="OFFICE">Office</option>
            <option value="OTHER">Other</option>
          </select>
        </div>

        {["street", "city", "state", "zipCode", "country"].map((field) => (
          <div className="mb-3" key={field}>
            <label className="form-label text-capitalize">{field}</label>
            <input
              type="text"
              className="form-control"
              name={field}
              value={address[field]}
              onChange={handleChange}
              required
            />
          </div>
        ))}

        <button type="submit" className="btn btn-primary w-100">
          Save Address
        </button>
      </form>
    </div>
  );
};

export default Address;
