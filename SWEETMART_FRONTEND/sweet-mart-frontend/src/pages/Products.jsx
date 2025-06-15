import { useEffect, useState } from 'react';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/products.css';

function Products() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const token = localStorage.getItem("token");
  const username = localStorage.getItem("username");

  const fetchProducts = (endpoint = "allproduct") => {
    setLoading(true);
    axios.get(`http://localhost:8080/products/${endpoint}`, {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => {
        setProducts(res.data);
        setLoading(false);
      })
      .catch(err => {
        const message = err.response?.status === 401
          ? "Unauthorized. Please log in again."
          : "Failed to fetch products.";
        setError(message);
        setLoading(false);
        toast.error(message);
      });
  };

  useEffect(() => {
    if (!token) {
      setError("You are not logged in. Please log in first.");
      setLoading(false);
      toast.error("You are not logged in. Please log in first.");
      return;
    }
    fetchProducts(); // default to all products
  }, [token]);

  const handleSortChange = (e) => {
    const value = e.target.value;
    if (value === "all") {
      fetchProducts("allproduct");
    } else if (value === "price") {
      fetchProducts("allproduct/sortbyprice");
    } else if (value === "name") {
      fetchProducts("allproduct/sortbyname");
    }
  };

  const addToCart = async (productName) => {
    if (!token || !username) {
      toast.warn("Please log in to add items to your cart.");
      return;
    }
    try {
      await axios.post(
        `http://localhost:8080/cart/user/add/${username}/${productName}?quantity=1`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      toast.success("Added to cart!");
    } catch (err) {
      toast.error(
        err.response?.status === 401
          ? "Unauthorized. Please log in again."
          : "Failed to add to cart."
      );
    }
  };

  if (loading) return <p>Loading products...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;

  return (
    <div>
      <h2>Products</h2>

      <div style={{ marginBottom: '1rem' }}>
        <select onChange={handleSortChange} defaultValue="all">
          <option value="all">All Products</option>
          <option value="price">Sort by Price</option>
          <option value="name">Sort by Name</option>
        </select>
      </div>

      <div className="products-container" style={{ display: "flex", flexWrap: "wrap", gap: "16px" }}>
        {products.length === 0 ? (
          <p>No products found.</p>
        ) : (
          products.map(p => (
            <div key={p.productId} className="product-card" style={{ border: "1px solid #ccc", padding: "16px", borderRadius: "8px", width: "220px" }}>
              <h3>{p.productName}</h3>
              <p>{p.productDescription}</p>
              <p>₹{p.productPrice}</p>
              <button onClick={() => addToCart(p.productName)}>Add to Cart</button>
            </div>
          ))
        )}
      </div>

      <ToastContainer position="top-right" autoClose={3000} />
    </div>
  );
}

export default Products;
