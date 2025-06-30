import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import ApiService from "../../service/ApiService";
import ReviewForm from "../pages/review/ReviewForm";
import ReviewList from "../pages/review/ReviewList";
import Spinner from "./Spinner";
import { useAuth } from "../context/AuthContext";

const ProductDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const { auth } = useAuth();

  useEffect(() => {
    ApiService.getProductById(id)
      .then((res) => {
        let productData = [];

        if (res && res.product) {
          productData = res.product;
        } else if (res && res.data && res.data.product) {
          productData = res.data.product;
        }

        setProduct(productData);
      })
      .catch((err) => console.error("Failed to fetch product", err));
  }, [id]);

  const handleAddToCart = async () => {
    const token = auth.token;
    const role = auth.role;

    if (!token) {
      alert("Please log in as a customer to add items to the cart.");
      navigate("/login");
      return;
    }

    if (role === "SELLER") {
      alert("Sellers are not allowed to add items to the cart.");
      return;
    }

    if (role === "ADMIN") {
      alert("Admins are not allowed to add items to the cart.");
      return;
    }

    try {
      const userId = localStorage.getItem("userId");
      const response = await ApiService.getCartByUserId(userId);
      const cartId = response.cartId;

      await ApiService.addItemToCart(cartId, id, quantity);

      alert(`${product.productName} added to cart successfully!`);
    } catch (err) {
      console.error("Error adding item to cart:", err);
      alert("Failed to add item to cart. Please try again.");
    }
  };

  if (!product) return <Spinner />;

  const {
    productName,
    brand,
    price,
    description,
    imageUrl,
    inventory,
    sellerDetails,
    categoryName,
  } = product;

  return (
    <div className="container mt-4">
      <button
        className="btn btn-outline-secondary mb-3"
        onClick={() => navigate(-1)}
      >
        ← Back
      </button>

      <div className="row">
        <div className="col-md-5 ">
          <img
            src={
              imageUrl?.replace(/^"+|"+$/g, "") ||
              "https://via.placeholder.com/300"
            }
            alt={productName}
            className="img-fluid rounded"
            style={{
              width: "100%",
              height: "350px",
              objectFit: "cover",
              border: "1px solid #ddd",
            }}
          />
        </div>

        <div className="col-md-7">
          <h2>{productName}</h2>
          <p className="text-muted">
            Brand: {brand} | Category: {categoryName}
          </p>
          <h4 className="text-success mb-3">₹ {price.toLocaleString()}</h4>

          <p>{description}</p>

          <p>
            <strong>Availability: </strong>
            {inventory > 0 ? (
              <span className="text-success">In stock ({inventory} left)</span>
            ) : (
              <span className="text-danger">Out of stock</span>
            )}
          </p>

          {sellerDetails && (
            <div className="mb-3">
              <h6>Seller Info:</h6>
              <ul className="list-unstyled">
                <li>
                  <strong>Shop:</strong> {sellerDetails.shopName}
                </li>
                <li>
                  <strong>Address:</strong> {sellerDetails.shopAddress}
                </li>
              </ul>
            </div>
          )}

          <div className="d-flex align-items-center mb-3">
            <label className="me-2">Quantity:</label>
            <input
              type="number"
              value={quantity}
              onChange={(e) => setQuantity(Math.max(1, +e.target.value))}
              min="1"
              max={inventory}
              className="form-control w-auto"
              style={{ maxWidth: "80px" }}
              disabled={inventory === 0}
            />
          </div>

          <button
            className="btn btn-secondary"
            disabled={inventory === 0}
            onClick={handleAddToCart}
          >
            Add to Cart
          </button>
        </div>
      </div>

      {/* Review Section */}
      <div className="mt-5">
        <h4 className="mb-3">Customer Reviews</h4>
        <ReviewList productId={id} />

        {auth?.role === "USER" && (
          <div className="mt-4">
            <h5>Leave a Review</h5>
            <ReviewForm
              productId={id}
              userId={localStorage.getItem("userId")}
              onReviewSubmit={() => window.location.reload()}
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default ProductDetails;
