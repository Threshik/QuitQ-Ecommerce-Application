import React, { useEffect, useState } from "react";

import ApiService from "../../../service/ApiService";
import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";

const Profile = () => {
  const { login, auth } = useAuth();
  const userId = auth?.userId;

  const [user, setUser] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phoneNumber: "",
    gender: "MALE",
  });

  const navigate = useNavigate();

  useEffect(() => {
    if (!userId) return;

    ApiService.getUserById(userId)
      .then((res) => {
        const userData = res.user;
        setUser(userData);

        setFormData({
          name: userData.name || "",
          email: userData.email || "",
          phoneNumber: userData.phoneNumber || "",
          gender: userData.gender || "MALE",
        });
      })
      .catch((err) => {
        console.error("Error fetching user:", err);
      });
  }, [userId]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSave = async () => {
    try {
      await ApiService.updateUser(userId, formData);

      const res = await ApiService.getUserById(userId);
      const userData = res.user;
      setUser(userData);
      setFormData({
        name: userData.name || "",
        email: userData.email || "",
        phoneNumber: userData.phoneNumber || "",
        gender: userData.gender || "MALE",
      });
      login(auth.token, auth.role, auth.userId, formData.name);
      setIsEditing(false);
      alert("Profile updated successfully!");
    } catch (err) {
      console.error("Error updating user:", err);
      alert("Update failed. Try again.");
    }
  };

  if (!user) return <p>Loading profile...</p>;

  return (
    <div className="container my-4">
      <h2>User Profile</h2>

      <div className="card mb-4">
        <div className="card-header d-flex justify-content-between">
          <span>Basic Information</span>
          {!isEditing ? (
            <button
              className="btn btn-sm btn-primary"
              onClick={() => setIsEditing(true)}
            >
              Edit Info
            </button>
          ) : (
            <button className="btn btn-sm btn-success" onClick={handleSave}>
              Save
            </button>
          )}
        </div>

        <div className="card-body">
          {!isEditing ? (
            <>
              <p>
                <strong>Name:</strong> {user.name}
              </p>
              <p>
                <strong>Email:</strong> {user.email}
              </p>
              <p>
                <strong>Phone:</strong> {user.phoneNumber}
              </p>
              <p>
                <strong>Gender:</strong> {user.gender}
              </p>
              <p>
                <strong>Role:</strong> {user.role}
              </p>
              <p>
                <strong>Registered On:</strong>{" "}
                {new Date(user.registrationDate).toLocaleDateString()}
              </p>
            </>
          ) : (
            <>
              <div className="mb-2">
                <label>Name</label>
                <input
                  name="name"
                  className="form-control"
                  value={formData.name}
                  onChange={handleChange}
                />
              </div>
              <div className="mb-2">
                <label>Email</label>
                <input
                  name="email"
                  className="form-control"
                  value={formData.email}
                  onChange={handleChange}
                />
              </div>
              <div className="mb-2">
                <label>Phone</label>
                <input
                  name="phoneNumber"
                  className="form-control"
                  value={formData.phoneNumber}
                  onChange={handleChange}
                />
              </div>
              <div className="mb-2">
                <label>Gender</label>
                <select
                  name="gender"
                  className="form-control"
                  value={formData.gender}
                  onChange={handleChange}
                >
                  <option value="MALE">Male</option>
                  <option value="FEMALE">Female</option>
                  <option value="OTHER">Other</option>
                </select>
              </div>
            </>
          )}
        </div>
      </div>

      {user.address && (
        <div className="card mb-4">
          <div className="card-header d-flex justify-content-between">
            <span>Saved Address</span>
            <button
              className="btn btn-sm btn-primary"
              onClick={() =>
                navigate("/user/address", { state: { from: "profile" } })
              }
            >
              Edit Address
            </button>
          </div>
          <div className="card-body">
            <p>
              <strong>Type:</strong> {user.address.addressType}
            </p>
            <p>
              <strong>Street:</strong> {user.address.street}
            </p>
            <p>
              <strong>City:</strong> {user.address.city}
            </p>
            <p>
              <strong>State:</strong> {user.address.state}
            </p>
            <p>
              <strong>Zip Code:</strong> {user.address.zipCode}
            </p>
            <p>
              <strong>Country:</strong> {user.address.country}
            </p>
          </div>
        </div>
      )}

      {user.orderDtos?.length > 0 && (
        <div className="card mb-4">
          <div className="card-header">Order History</div>
          <div className="card-body">
            {user.orderDtos.map((order) => (
              <div key={order.id} className="mb-3 border-bottom pb-2">
                <p>
                  <strong>Order ID:</strong> {order.id}
                </p>
                <p>
                  <strong>Date:</strong>{" "}
                  {new Date(order.orderDate).toLocaleString()}
                </p>
                <p>
                  <strong>Status:</strong> {order.status}
                </p>
                <p>
                  <strong>Total:</strong> ₹{order.totalPrice.toLocaleString()}
                </p>
                <p>
                  <strong>Payment:</strong> {order.payment.paymentMethod} -{" "}
                  {order.payment.paymentStatus}
                </p>
                <h6>Items:</h6>
                <ul>
                  {order.orderItems.map((item, idx) => (
                    <li key={idx}>
                      {item.productName} x {item.quantity} — ₹{item.price}
                    </li>
                  ))}
                </ul>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};

export default Profile;
