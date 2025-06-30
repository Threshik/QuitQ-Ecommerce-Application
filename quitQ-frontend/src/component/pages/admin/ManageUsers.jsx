import React, { useEffect, useState } from "react";

import ApiService from "../../../service/ApiService";
import { useAuth } from "../../context/AuthContext";

export default function ManageUsers() {
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [filter, setFilter] = useState("ALL");
  const { auth } = useAuth();

  const currentUserId = auth.userId;

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const response = await ApiService.getAllUsers();
      const userList = response.userList;
      setUsers(Array.isArray(userList) ? userList : []);
    } catch (err) {
      console.error("Error fetching users", err);
      alert("Error fetching users");
    }
  };

  const handleView = (user) => {
    setSelectedUser(user);
  };

  const filteredUsers =
    filter === "ALL" ? users : users.filter((u) => u.role === filter);

  return (
    <div className="container mt-4">
      <h2 className="mb-4 text-center">Manage Users</h2>

      {/* Filter Buttons */}
      <div className="mb-3 text-center">
        {["ALL", "USER", "SELLER", "ADMIN"].map((role) => (
          <button
            key={role}
            className={`btn btn-sm mx-2 ${
              filter === role ? "btn-primary" : "btn-outline-primary"
            }`}
            onClick={() => setFilter(role)}
          >
            {role}
          </button>
        ))}
      </div>

      {/* User Table */}
      <table className="table table-bordered">
        <thead className="table-light">
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th>Registered</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {filteredUsers.length > 0 ? (
            filteredUsers.map((user) => (
              <tr
                key={user.id}
                className={user.id === currentUserId ? "table-warning" : ""}
              >
                <td>{user.id}</td>
                <td>{user.name}</td>
                <td>{user.email}</td>
                <td>{user.role}</td>
                <td>
                  {user.registrationDate
                    ? new Date(user.registrationDate).toLocaleDateString()
                    : "N/A"}
                </td>
                <td>
                  <button
                    className="btn btn-sm btn-secondary"
                    onClick={() => handleView(user)}
                  >
                    View
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="6" className="text-center">
                No users found
              </td>
            </tr>
          )}
        </tbody>
      </table>

      {/* User Details Below Table */}
      {selectedUser && (
        <div className="card mt-4">
          <div className="card-header">
            <h5>User Details - {selectedUser.name}</h5>
          </div>
          <div className="card-body">
            <p>
              <strong>ID:</strong> {selectedUser.id}
            </p>
            <p>
              <strong>Email:</strong> {selectedUser.email}
            </p>
            <p>
              <strong>Phone:</strong> {selectedUser.phoneNumber || "N/A"}
            </p>
            <p>
              <strong>Gender:</strong> {selectedUser.gender || "N/A"}
            </p>
            <p>
              <strong>Role:</strong> {selectedUser.role}
            </p>
            <p>
              <strong>Registered On:</strong>{" "}
              {selectedUser.registrationDate
                ? new Date(selectedUser.registrationDate).toLocaleDateString()
                : "N/A"}
            </p>
          </div>
        </div>
      )}
    </div>
  );
}
