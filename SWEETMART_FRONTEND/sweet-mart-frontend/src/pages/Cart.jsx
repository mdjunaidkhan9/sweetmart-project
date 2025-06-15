import { useEffect, useState } from 'react';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/cart.css';

function Cart() {
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const token = localStorage.getItem("token");
  const username = localStorage.getItem("username");

  const fetchCart = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/cart/user/${username}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setCartItems(response.data);
    } catch (err) {
      setError("Failed to fetch cart items.");
      toast.error("Failed to fetch cart items.");
      console.error("Fetch cart error:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (!token || !username) {
      setError("You are not logged in.");
      setLoading(false);
      toast.error("You are not logged in.");
      return;
    }
    fetchCart();
  }, [token, username]);

  const updateQuantity = async (productName, newQty) => {
    if (newQty <= 0) {
      toast.warn("Quantity must be at least 1");
      return;
    }

    try {
      await axios.put(
        `http://localhost:8080/cart/user/update/${username}/${productName}?quantity=${newQty}`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      await fetchCart();
      toast.success("Quantity updated");
    } catch (err) {
      console.error("Quantity update failed:", err);
      toast.error("Failed to update quantity");
    }
  };

  const removeItem = async (productName) => {
    try {
      await axios.delete(
        `http://localhost:8080/cart/user/remove/${username}/${productName}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      await fetchCart();
      toast.success("Item removed from cart");
    } catch (err) {
      console.error("Remove item failed:", err);
      toast.error("Failed to remove item");
    }
  };

  const placeOrder = async () => {
    try {
      await axios.post(
        `http://localhost:8080/orders/user/place/${username}`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      await fetchCart();
      toast.success("Order placed successfully!");
    } catch (err) {
      console.error("Place order failed:", err);
      toast.error("Failed to place order.");
    }
  };

  const total = Array.isArray(cartItems)
    ? cartItems.reduce((sum, item) => sum + item.productPrice * item.quantity, 0)
    : 0;

  if (loading) return <p>Loading cart...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;

  return (
    <div className="cart-container">
      <h2 className="cart-title">{username}'s Cart</h2>

      {cartItems.length === 0 ? (
        <p className="cart-empty">Your cart is empty.</p>
      ) : (
        <>
          <div className="cart-list">
            {cartItems.map(item => (
              <div className="cart-item-card" key={item.id}>
                <div className="cart-item-details">
                  <span className="cart-item-name">{item.productName}</span>
                  <span className="cart-item-meta">₹{item.productPrice}</span>
                  <span className="cart-item-qty">
                    Qty: {item.quantity}
                    <button onClick={() => updateQuantity(item.productName, item.quantity - 1)} style={{ marginLeft: '10px' }}>−</button>
                    <button onClick={() => updateQuantity(item.productName, item.quantity + 1)} style={{ marginLeft: '5px' }}>+</button>
                  </span>
                </div>
                <button
                  className="cart-remove-btn"
                  onClick={() => removeItem(item.productName)}
                >
                  Remove
                </button>
              </div>
            ))}
          </div>

          <h3 style={{ marginTop: '40px' }}>Total: ₹{total}</h3>

          <button
            className="place-order-btn"
            onClick={placeOrder}
            style={{
              marginTop: '20px',
              padding: '10px 20px',
              fontWeight: 'bold',
              backgroundColor: '#2196f3',
              color: 'white',
              border: 'none',
              borderRadius: '8px'
            }}
          >
            Place Order
          </button>
        </>
      )}

      <ToastContainer position="top-right" autoClose={3000} />
    </div>
  );
}

export default Cart;
