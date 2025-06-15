import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/categories.css';

const Categories = () => {
  const [categoryList, setCategoryList] = useState([]);
  const [loading, setLoading] = useState(true);
  const token = localStorage.getItem('token');
  const username = localStorage.getItem('username');

  useEffect(() => {
    fetchCategoriesWithProducts();
  }, []);

  const fetchCategoriesWithProducts = async () => {
    try {
      const response = await axios.get('http://localhost:8080/category/all-with-products', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setCategoryList(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching categories:', error);
      setLoading(false);
    }
  };

  const addToCart = async (productName) => {
    try {
      await axios.post(
        `http://localhost:8080/cart/user/add/${username}/${productName}?quantity=1`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      toast.success("Added to cart!");
    } catch (error) {
      console.error('Add to cart error:', error);
      toast.error("Failed to add to cart.");
    }
  };

  if (loading) return <div>Loading categories...</div>;

  return (
    <div className="category-container">
      <h2>Categories & Products</h2>
      {categoryList.length === 0 ? (
        <p>No categories found.</p>
      ) : (
        categoryList.map((category) => (
          <div key={category.categoryId} className="category-box">
            <h3>{category.categoryName}</h3>
            <p>{category.categoryDescription}</p>

            {category.products && category.products.length > 0 ? (
              <ul>
                {category.products.map((product) => (
                  <li key={product.productId} style={{ marginBottom: "10px" }}>
                    <strong>{product.productName}</strong> - ₹{product.productPrice}<br />
                    <em>{product.productDescription}</em><br />
                    <button onClick={() => addToCart(product.productName)}>Add to Cart</button>
                  </li>
                ))}
              </ul>
            ) : (
              <p>No products in this category.</p>
            )}
          </div>
        ))
      )}
      <ToastContainer position="top-right" autoClose={3000} />
    </div>
  );
};

export default Categories;
