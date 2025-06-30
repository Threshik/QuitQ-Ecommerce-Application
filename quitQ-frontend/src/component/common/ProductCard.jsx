import "../../style/Home.css";

import React from "react";
import { useNavigate } from "react-router-dom";

const ProductCard = ({ product }) => {
  const { id, productName, price, brand, imageUrl, inventory } = product;
  const navigate = useNavigate();

  const handleViewDetails = () => {
    if (inventory > 0) {
      navigate(`/product/${id}`);
    }
  };

  return (
    <div className="card card-hover h-100 shadow-sm">
      <img
        src={
          imageUrl?.replace(/^"+|"+$/g, "") || "https://via.placeholder.com/200"
        }
        alt={productName}
        className="card-img-top"
        style={{ height: "200px", objectFit: "cover" }}
      />
      <div className="card-body d-flex flex-column">
        <h5 className="card-title">{productName}</h5>
        <p className="card-text mb-1">Brand: {brand}</p>
        <p className="card-text mb-1">Price: ₹{price}</p>
        <button
          className="btn btn-secondary mt-auto"
          onClick={handleViewDetails}
          disabled={inventory <= 0}
        >
          {inventory > 0 ? "View Details" : "Unavailable"}
        </button>
      </div>
    </div>
  );
};

export default ProductCard;
