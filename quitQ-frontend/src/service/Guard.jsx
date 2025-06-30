import { Navigate, useLocation } from "react-router-dom";

import React from "react";
import { useAuth } from "../component/context/AuthContext";

// General auth guard
export const Guard = ({ children }) => {
  const { auth } = useAuth();
  const location = useLocation();

  return auth.token ? (
    children
  ) : (
    <Navigate to="/login" state={{ from: location }} replace />
  );
};

// Admin route guard
export const AdminRoute = ({ children }) => {
  const { auth } = useAuth();
  const location = useLocation();

  return auth.token && auth.role === "ADMIN" ? (
    children
  ) : (
    <Navigate to="/login" replace state={{ from: location }} />
  );
};

// Seller route guard
export const SellerRoute = ({ children }) => {
  const { auth } = useAuth();
  const location = useLocation();

  return auth.token && auth.role === "SELLER" ? (
    children
  ) : (
    <Navigate to="/login" replace state={{ from: location }} />
  );
};
