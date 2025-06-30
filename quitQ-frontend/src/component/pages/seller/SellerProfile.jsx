import React, { useEffect, useState } from "react";

import ApiService from "../../../service/ApiService";
import { useAuth } from "../../context/AuthContext";

const SellerProfile = () => {
  const { login, auth } = useAuth();
  const userId = auth?.userId;

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phoneNumber: "",
    gender: "",
    shopName: "",
    businessLicense: "",
    gstNumber: "",
    shopAddress: "",
  });

  const [editing, setEditing] = useState(false);

  useEffect(() => {
    if (userId) {
      loadSellerData();
    }
  }, [userId]);

  const loadSellerData = async () => {
    try {
      const response = await ApiService.getUserById(userId);
      const user = response.user;
      const seller = user.sellerDetails || {};

      setFormData({
        name: user.name || "",
        email: user.email || "",
        phoneNumber: user.phoneNumber || "",
        gender: user.gender || "",
        shopName: seller.shopName || "",
        businessLicense: seller.businessLicense || "",
        gstNumber: seller.gstNumber || "",
        shopAddress: seller.shopAddress || "",
      });
    } catch (error) {
      console.error("Failed to load seller data", error);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleUpdate = async () => {
    try {
      await ApiService.updateSeller(userId, formData);
      alert("Profile updated successfully!");
      login(auth.token, auth.role, auth.userId, formData.name);
      setEditing(false);
    } catch (error) {
      console.error("Update failed", error);
      alert("Failed to update profile");
    }
  };

  return (
    <div className="container mt-4">
      <div className="card shadow">
        <div className="card-header d-flex justify-content-between align-items-center">
          <h4>Seller Profile</h4>
          {!editing && (
            <button
              className="btn btn-primary btn-sm"
              onClick={() => setEditing(true)}
            >
              Edit
            </button>
          )}
        </div>

        <div className="card-body">
          {editing ? (
            <>
              <div className="mb-3">
                <label className="form-label">Name</label>
                <input
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleChange}
                  className="form-control"
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Email</label>
                <input
                  type="text"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  className="form-control"
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Phone Number</label>
                <input
                  type="text"
                  name="phoneNumber"
                  value={formData.phoneNumber}
                  onChange={handleChange}
                  className="form-control"
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Gender</label>
                <input
                  type="text"
                  name="gender"
                  value={formData.gender}
                  onChange={handleChange}
                  className="form-control"
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Shop Name</label>
                <input
                  type="text"
                  name="shopName"
                  value={formData.shopName}
                  onChange={handleChange}
                  className="form-control"
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Business License</label>
                <input
                  type="text"
                  name="businessLicense"
                  value={formData.businessLicense}
                  onChange={handleChange}
                  className="form-control"
                />
              </div>

              <div className="mb-3">
                <label className="form-label">GST Number</label>
                <input
                  type="text"
                  name="gstNumber"
                  value={formData.gstNumber}
                  onChange={handleChange}
                  className="form-control"
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Shop Address</label>
                <input
                  type="text"
                  name="shopAddress"
                  value={formData.shopAddress}
                  onChange={handleChange}
                  className="form-control"
                />
              </div>

              <button className="btn btn-success me-2" onClick={handleUpdate}>
                Save
              </button>
              <button
                className="btn btn-outline-danger"
                onClick={() => setEditing(false)}
              >
                Cancel
              </button>
            </>
          ) : (
            <>
              <p>
                <strong>Name:</strong> {formData.name}
              </p>
              <p>
                <strong>Email:</strong> {formData.email}
              </p>
              <p>
                <strong>Phone Number:</strong> {formData.phoneNumber}
              </p>
              <p>
                <strong>Gender:</strong> {formData.gender}
              </p>
              <p>
                <strong>Shop Name:</strong> {formData.shopName}
              </p>
              <p>
                <strong>Business License:</strong> {formData.businessLicense}
              </p>
              <p>
                <strong>GST Number:</strong> {formData.gstNumber}
              </p>
              <p>
                <strong>Shop Address:</strong> {formData.shopAddress}
              </p>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default SellerProfile;
