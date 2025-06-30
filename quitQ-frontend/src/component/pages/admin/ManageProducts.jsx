import React, { useEffect, useState } from "react";

import ApiService from "../../../service/ApiService";
import ProductFormModal from "../seller/ProductFormModal";

const ManageProducts = () => {
  const [products, setProducts] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);

  const fetchProducts = async () => {
    try {
      const data = await ApiService.getAllProducts();
      setProducts(data.productList || []);
    } catch (error) {
      console.error("Error fetching products", error);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to delete this product?")) {
      try {
        await ApiService.deleteProduct(id);
        fetchProducts();
      } catch (error) {
        alert("Error deleting product: " + error.message);
      }
    }
  };

  const handleAdd = () => {
    setSelectedProduct(null);
    setShowModal(true);
  };

  const handleEdit = (product) => {
    setSelectedProduct(product);
    setShowModal(true);
  };

  const handleModalClose = () => {
    setShowModal(false);
    setSelectedProduct(null);
    fetchProducts();
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  return (
    <div className="container mt-5">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2 className="mb-0">Manage Products</h2>
        <button className="btn btn-primary" onClick={handleAdd}>
          Add Product
        </button>
      </div>

      <div className="table-responsive">
        <table className="table table-striped table-bordered align-middle text-center">
          <thead className="table-light">
            <tr>
              <th>Id</th>
              <th style={{ width: "80px" }}>Image</th>
              <th>Product</th>
              <th style={{ maxWidth: "200px" }}>Description</th>
              <th>Price (₹)</th>
              <th>Brand</th>
              <th>Inventory</th>
              <th>Category</th>
              <th>Seller</th>
              <th style={{ width: "140px" }}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {products.length === 0 ? (
              <tr>
                <td colSpan="10" className="text-center">
                  No products found.
                </td>
              </tr>
            ) : (
              products.map((prod, index) => (
                <tr key={prod.id}>
                  <td>{index + 1}</td>
                  <td>
                    <img
                      src={prod.imageUrl}
                      alt={prod.productName}
                      className="img-fluid rounded"
                      style={{ maxWidth: "60px", height: "auto" }}
                    />
                  </td>
                  <td>{prod.productName}</td>
                  <td
                    style={{
                      maxWidth: "200px",
                      overflow: "hidden",
                      textOverflow: "ellipsis",
                      whiteSpace: "nowrap",
                    }}
                  >
                    {prod.description}
                  </td>
                  <td>{prod.price}</td>
                  <td>{prod.brand}</td>
                  <td>{prod.inventory}</td>
                  <td>{prod.categoryName}</td>
                  <td>
                    {prod.sellerDetails && prod.sellerDetails.shopName
                      ? prod.sellerDetails.shopName
                      : "—"}
                  </td>
                  <td>
                    <div className="d-flex justify-content-center gap-2">
                      <button
                        className="btn btn-sm btn-primary"
                        onClick={() => handleEdit(prod)}
                      >
                        Edit
                      </button>
                      <button
                        className="btn btn-sm btn-danger"
                        onClick={() => handleDelete(prod.id)}
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {showModal && (
        <ProductFormModal
          product={selectedProduct}
          onClose={handleModalClose}
        />
      )}
    </div>
  );
};

export default ManageProducts;
