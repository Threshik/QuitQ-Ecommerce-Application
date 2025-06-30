import React, { useEffect, useState } from "react";

import ApiService from "../../../service/ApiService";
import ProductFormModal from "./ProductFormModal";

const SellerProducts = () => {
  const [products, setProducts] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [editingProduct, setEditingProduct] = useState(null);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const response = await ApiService.getSellerProducts();
      setProducts(response.productList || []);
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };

  const handleAddProduct = () => {
    setEditingProduct(null);
    setShowForm(true);
  };

  const handleEdit = (product) => {
    setEditingProduct(product);
    setShowForm(true);
  };

  const handleDelete = async (productId) => {
    if (window.confirm("Are you sure you want to delete this product?")) {
      try {
        await ApiService.deleteProduct(productId);
        fetchProducts();
      } catch (error) {
        console.error("Delete failed:", error);
        alert("Failed to delete product.");
      }
    }
  };

  return (
    <div className="container py-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2 className="d-flex align-items-center gap-2">
          <i className=" text-primary"></i>
          My Products
        </h2>
        <button
          className="btn btn-primary d-flex align-items-center gap-1"
          onClick={handleAddProduct}
        >
          <i className="bi bi-plus-circle-fill"></i>
          Add Product
        </button>
      </div>

      {products.length === 0 ? (
        <div className="alert alert-info text-center">No products found.</div>
      ) : (
        <div className="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
          {products.map((product) => (
            <div className="col" key={product.id}>
              <div className="card h-100 shadow-sm border-0">
                <img
                  src={product.imageUrl}
                  className="card-img-top"
                  alt={product.productName}
                  style={{
                    height: "170px",
                    objectFit: "cover",
                    objectPosition: "center",
                    borderRadius: "0.5rem",
                    backgroundColor: "#f8f9fa",
                  }}
                />
                <div className="card-body p-3">
                  <h6 className="card-title mb-1 text-dark fw-semibold">
                    {product.productName}
                  </h6>
                  <p className="text-muted small mb-2">
                    {product.description?.length > 45
                      ? product.description.slice(0, 45) + "..."
                      : product.description}
                  </p>
                  <div className="small text-muted mb-1">
                    <strong>₹{product.price}</strong> &nbsp; | &nbsp;
                    <span>{product.brand}</span>
                  </div>
                  <div className="small text-muted">
                    <span>Stock: {product.inventory}</span>
                  </div>
                  <div className="small text-muted">
                    <span>Category: {product.categoryName}</span>
                  </div>
                </div>
                <div className="card-footer bg-white border-top-0 d-flex justify-content-center gap-2 pb-3">
                  <button
                    className="btn btn-sm btn-outline-primary d-flex align-items-center gap-1"
                    onClick={() => handleEdit(product)}
                  >
                    <i className="bi bi-pencil-square"></i> Edit
                  </button>
                  <button
                    className="btn btn-sm btn-outline-danger d-flex align-items-center gap-1"
                    onClick={() => handleDelete(product.id)}
                  >
                    <i className="bi bi-trash"></i> Delete
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      {showForm && (
        <ProductFormModal
          product={editingProduct}
          onClose={() => {
            setShowForm(false);
            fetchProducts();
          }}
        />
      )}
    </div>
  );
};

export default SellerProducts;
