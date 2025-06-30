import React, { useEffect, useState } from "react";

import ApiService from "../../service/ApiService";

const CategoryList = ({ onCategorySelect }) => {
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);

  useEffect(() => {
    ApiService.getAllCategories().then((res) => {
      let list = [];
      if (res && res.categoryList) {
        list = res.categoryList;
      }
      setCategories(list);
    });
  }, []);

  const handleAllClick = () => {
    setSelectedCategory(null);
    onCategorySelect(null);
  };

  const handleCategoryClick = (categoryId) => {
    setSelectedCategory(categoryId);
    onCategorySelect(categoryId);
  };

  return (
    <div className="mb-4">
      <h4 className="mb-3">Filter by Category</h4>
      <div className="d-flex flex-wrap gap-2">
        <button
          onClick={handleAllClick}
          className={`btn ${
            selectedCategory === null
              ? "btn-secondary"
              : "btn-outline-secondary"
          }`}
        >
          All
        </button>

        {categories.map((category) => (
          <button
            key={category.id}
            onClick={() => handleCategoryClick(category.id)}
            className={`btn ${
              selectedCategory === category.id
                ? "btn-secondary"
                : "btn-outline-secondary"
            }`}
          >
            {category.name}
          </button>
        ))}
      </div>
    </div>
  );
};

export default CategoryList;
