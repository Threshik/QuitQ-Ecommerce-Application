import "../../style/Home.css"; // Adjust path based on actual structure

import React, { useEffect, useRef, useState } from "react";

import ApiService from "../../service/ApiService";
import CategoryList from "./CategoryList";
import ProductCard from "./ProductCard";
import Spinner from "./Spinner";
import { useLocation } from "react-router-dom";

const Home = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const productSectionRef = useRef(null);

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const urlSearch = queryParams.get("search") || "";

  const fetchAllProducts = () => {
    setLoading(true);
    ApiService.getAllProducts()
      .then((res) => {
        const productList =
          res?.data?.productList || res?.productList || res || [];
        setProducts(productList);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching products:", err);
        setLoading(false);
      });
  };

  const fetchByCategory = (categoryId) => {
    if (!categoryId) {
      fetchAllProducts();
      return;
    }
    setLoading(true);
    ApiService.getProductsByCategory(categoryId)
      .then((res) => {
        const productList =
          res?.data?.productList || res?.productList || res || [];
        setProducts(productList);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching products by category:", err);
        setLoading(false);
      });
  };

  const handleShopNowClick = () => {
    productSectionRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    fetchAllProducts();
  }, [urlSearch]);

  const filteredProducts = products.filter((product) =>
    product.productName.toLowerCase().includes(urlSearch.toLowerCase())
  );

  return (
    <div className="container mt-4">
      {/* Hero Carousel */}
      <div
        id="heroCarousel"
        className="carousel slide mb-5 shadow"
        data-bs-ride="carousel"
      >
        <div className="carousel-inner">
          <div className="carousel-item active">
            <img
              src="/assets/banner7.png"
              className="d-block w-100"
              alt="Banner 1"
            />
            <div className="carousel-caption d-none d-md-block">
              <button
                className="btn btn-light btn-lg mt-3"
                onClick={handleShopNowClick}
              >
                Shop Now
              </button>
            </div>
          </div>
          <div className="carousel-item">
            <img
              src="/assets/banner1.png"
              className="d-block w-100"
              alt="Banner 2"
            />
          </div>
          <div className="carousel-item">
            <img
              src="/assets/banner3.png"
              className="d-block w-100"
              alt="Banner 3"
            />
          </div>
          <div className="carousel-item">
            <img
              src="/assets/banner4.png"
              className="d-block w-100"
              alt="Banner 4"
            />
          </div>
          <div className="carousel-item">
            <img
              src="/assets/banner5.png"
              className="d-block w-100"
              alt="Banner 5"
            />
          </div>
          <div className="carousel-item">
            <img
              src="/assets/banner6.png"
              className="d-block w-100"
              alt="Banner 6"
            />
          </div>
        </div>

        {/* Carousel controls */}
        <button
          className="carousel-control-prev"
          type="button"
          data-bs-target="#heroCarousel"
          data-bs-slide="prev"
        >
          <span
            className="carousel-control-prev-icon"
            aria-hidden="true"
          ></span>
          <span className="visually-hidden">Previous</span>
        </button>
        <button
          className="carousel-control-next"
          type="button"
          data-bs-target="#heroCarousel"
          data-bs-slide="next"
        >
          <span
            className="carousel-control-next-icon"
            aria-hidden="true"
          ></span>
          <span className="visually-hidden">Next</span>
        </button>
      </div>

      {/* Categories */}
      <CategoryList onCategorySelect={fetchByCategory} />

      {/* All Products */}
      <h2 ref={productSectionRef} className="mt-5 mb-4 text-center text-dark">
        <span className="border-bottom border-secondary ">All Products</span>
      </h2>

      {loading ? (
        <Spinner />
      ) : filteredProducts.length === 0 ? (
        <p className="text-muted">No products found.</p>
      ) : (
        <div className="row g-4">
          {filteredProducts.map((product) => (
            <div className="col-sm-6 col-md-4 col-lg-3" key={product.id}>
              <ProductCard product={product} />
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Home;
