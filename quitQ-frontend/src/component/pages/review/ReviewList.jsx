import React, { useEffect, useState } from "react";

import ApiService from "../../../service/ApiService";

const ReviewList = ({ productId }) => {
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const response = await ApiService.getReviewsByProductId(productId);
        setReviews(response.reviewDtosList || []);
      } catch (error) {
        console.error("Error fetching reviews", error);
      }
    };

    fetchReviews();
  }, [productId]);

  // Helper to render stars
  const renderStars = (rating) => {
    const fullStars = "★".repeat(rating);
    const emptyStars = "☆".repeat(5 - rating);
    return fullStars + emptyStars;
  };

  return (
    <div className="mt-4">
      <h5 className="mb-3">Reviews</h5>
      {reviews.length === 0 ? (
        <p className="text-muted">No reviews yet.</p>
      ) : (
        <ul className="list-group">
          {reviews.map((review, idx) => (
            <li key={idx} className="list-group-item">
              <div className="fw-bold">
                {review.userName}{" "}
                <span className="text-warning">
                  {renderStars(review.rating)}
                </span>
              </div>
              <div>{review.comment}</div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default ReviewList;
