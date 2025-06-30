import React, { useState } from "react";

import ApiService from "../../../service/ApiService";
import { useAuth } from "../../context/AuthContext";

//callback prop - to referesh the review list
const ReviewForm = ({ productId, onReviewSubmit }) => {
  const { auth } = useAuth();
  const [rating, setRating] = useState(5);
  const [comment, setComment] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!auth.userId || !auth.name) {
      alert("Please login to submit a review.");
      return;
    }

    const reviewData = {
      productId,
      userId: parseInt(auth.userId),
      userName: auth.name,
      rating,
      comment,
    };

    try {
      await ApiService.createReview(reviewData);
      alert("Review submitted!");
      setComment("");
      setRating(5);

      if (onReviewSubmit) {
        onReviewSubmit();
      }
    } catch (error) {
      console.error("Error submitting review", error);
      alert("Failed to submit review.");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="border p-4 rounded shadow-sm">
      <h5 className="mb-3">Write a Review</h5>

      <div className="mb-3">
        <label htmlFor="rating" className="form-label">
          Rating
        </label>

        <select
          id="rating"
          className="form-select"
          value={rating}
          onChange={(e) => setRating(parseInt(e.target.value))}
        >
          <option value={5}>5 stars</option>
          <option value={4}>4 stars</option>
          <option value={3}>3 stars</option>
          <option value={2}>2 stars</option>
          <option value={1}>1 star</option>
        </select>
      </div>

      <div className="mb-3">
        <label htmlFor="comment" className="form-label">
          Your Review
        </label>
        <textarea
          id="comment"
          className="form-control"
          rows="4"
          placeholder="Write your review here..."
          value={comment}
          onChange={(e) => setComment(e.target.value)}
          required
        />
      </div>

      <button type="submit" className="btn btn-primary">
        Submit Review
      </button>
    </form>
  );
};

export default ReviewForm;
