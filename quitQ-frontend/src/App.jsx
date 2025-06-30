import "./App.css";

import { AdminRoute, Guard, SellerRoute } from "./service/Guard";
import {
  Route,
  BrowserRouter as Router,
  Routes,
  useLocation,
} from "react-router-dom";

import Address from "./component/pages/user/Address";
import AdminCategoryList from "./component/pages/admin/AdminCategoryList";
import AdminDashboard from "./component/pages/admin/AdminDashboard";
import Cart from "./component/pages/user/cart";
import Checkout from "./component/pages/user/Checkout";
import Footer from "./component/common/Footer";
import Home from "./component/common/Home";
import Login from "./component/common/Login";
import ManageProducts from "./component/pages/admin/ManageProducts";
import ManageUsers from "./component/pages/admin/ManageUsers";
import Navbar from "./component/common/Navbar";
import OrderList from "./component/pages/admin/OrderList";
import Orders from "./component/pages/user/orders";
import ProductDetails from "./component/common/ProductDetails";
import Profile from "./component/pages/user/profile";
import Register from "./component/common/Register";
import SellerDashboard from "./component/pages/seller/SellerDashboard";
import SellerOrders from "./component/pages/seller/SellerOrders";
import SellerProducts from "./component/pages/seller/SellerProducts";
import SellerProfile from "./component/pages/seller/SellerProfile";
import SellersList from "./component/pages/admin/SellersList";

function AppWrapper() {
  return (
    <Router>
      <App />
    </Router>
  );
}

function App() {
  const location = useLocation();

  const hideLayout =
    location.pathname === "/login" || location.pathname === "/register";

  return (
    <>
      {!hideLayout && <Navbar />}

      {/*Public Routes*/}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/home" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/product/:id" element={<ProductDetails />} />

        {/* USER Routes */}
        <Route
          path="/user/cart"
          element={
            <Guard>
              <Cart />
            </Guard>
          }
        />
        <Route
          path="/user/orders"
          element={
            <Guard>
              <Orders />
            </Guard>
          }
        />
        <Route
          path="/user/profile"
          element={
            <Guard>
              <Profile />
            </Guard>
          }
        />
        <Route
          path="/user/checkout"
          element={
            <Guard>
              <Checkout />
            </Guard>
          }
        />
        <Route
          path="/user/address"
          element={
            <Guard>
              <Address />
            </Guard>
          }
        />

        {/* SELLER Routes */}
        <Route
          path="/seller/dashboard"
          element={
            <SellerRoute>
              <SellerDashboard />
            </SellerRoute>
          }
        />
        <Route
          path="/seller/orders"
          element={
            <SellerRoute>
              <SellerOrders />
            </SellerRoute>
          }
        />
        <Route
          path="/seller/products"
          element={
            <SellerRoute>
              <SellerProducts />
            </SellerRoute>
          }
        />
        <Route
          path="/seller/profile"
          element={
            <SellerRoute>
              <SellerProfile />
            </SellerRoute>
          }
        />

        {/* ADMIN Routes */}
        <Route
          path="/admin/dashboard"
          element={
            <AdminRoute>
              <AdminDashboard />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/manage-users"
          element={
            <AdminRoute>
              <ManageUsers />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/manage-sellers"
          element={
            <AdminRoute>
              <SellersList />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/manage-products"
          element={
            <AdminRoute>
              <ManageProducts />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/manage-categories"
          element={
            <AdminRoute>
              <AdminCategoryList />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/manage-orders"
          element={
            <AdminRoute>
              <OrderList />
            </AdminRoute>
          }
        />
      </Routes>

      {!hideLayout && <Footer />}
    </>
  );
}

export default AppWrapper;
