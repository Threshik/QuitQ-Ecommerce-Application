import React, { useEffect, useState } from "react";

import ApiService from "../../../service/ApiService";

export default function ManageSellers() {
  const [sellers, setSellers] = useState([]);
  const [selectedSeller, setSelectedSeller] = useState(null);

  useEffect(() => {
    fetchSellersWithProducts();
  }, []);

  const fetchSellersWithProducts = async () => {
    try {
      const response = await ApiService.getAllSellersWithProducts();
      setSellers(Array.isArray(response.data) ? response.data : []);
    } catch (err) {
      console.error("Error fetching sellers", err);
      alert("Error fetching seller details");
    }
  };

  const handleView = (seller) => {
    setSelectedSeller(seller);
  };

  return (
    <div className="container mt-4">
      <h2 className="mb-4 text-center">Manage Sellers</h2>

      <table className="table table-bordered">
        <thead className="table-light">
          <tr>
            <th>Seller ID</th>
            <th>Shop Name</th>
            <th>Name</th>
            <th>Email</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {sellers.length > 0 ? (
            sellers.map((entry, index) => {
              const seller = entry.sellerDetails;
              return (
                <tr key={index}>
                  <td>{seller.sellerId}</td>
                  <td>{seller.shopName}</td>
                  <td>{seller.name}</td>
                  <td>{seller.email}</td>
                  <td>
                    <button
                      className="btn btn-sm btn-secondary"
                      onClick={() => handleView(entry)}
                    >
                      View
                    </button>
                  </td>
                </tr>
              );
            })
          ) : (
            <tr>
              <td colSpan="5" className="text-center">
                No sellers found
              </td>
            </tr>
          )}
        </tbody>
      </table>

      {/* Seller and Product Details */}
      {selectedSeller && (
        <div className="card mt-4">
          <div className="card-header">
            <h5>Seller Details - {selectedSeller.sellerDetails.shopName}</h5>
          </div>
          <div className="card-body">
            <p>
              <strong>Name:</strong> {selectedSeller.sellerDetails.name}
            </p>
            <p>
              <strong>Email:</strong> {selectedSeller.sellerDetails.email}
            </p>
            <p>
              <strong>Shop Address:</strong>{" "}
              {selectedSeller.sellerDetails.shopAddress}
            </p>

            <h6 className="mt-4">Products</h6>
            {selectedSeller.products.length > 0 ? (
              <div className="table-responsive">
                <table className="table table-striped table-bordered mt-2">
                  <thead className="table-light">
                    <tr>
                      <th>Product ID</th>
                      <th>Product Name</th>
                      <th>Brand</th>
                      <th>Price</th>
                      <th>Inventory</th>
                      <th>Category</th>
                      <th>Added On</th>
                    </tr>
                  </thead>
                  <tbody>
                    {selectedSeller.products.map((product) => (
                      <tr key={product.id}>
                        <td>{product.id}</td>
                        <td>{product.productName}</td>
                        <td>{product.brand}</td>
                        <td>₹{product.price}</td>
                        <td>{product.inventory}</td>
                        <td>{product.categoryName}</td>
                        <td>
                          {new Date(product.createdAt).toLocaleDateString()}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            ) : (
              <p className="text-danger">
                No products available for this seller.
              </p>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
