import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../css/adminorders.css';

function AdminOrders() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [expandedOrderId, setExpandedOrderId] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8080/orders/admin/allorders', {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => {
        setOrders(res.data);
        setLoading(false);
      })
      .catch(() => {
        setError('Failed to fetch orders.');
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Loading all orders...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;

  return (
    <div className="admin-orders-container">
    

      <main className="admin-orders-content">
        <h2>All Orders (Admin)</h2>

        {orders.length === 0 ? (
          <p>No orders found.</p>
        ) : (
          <div className="orders-grid">
            {orders.map((order) => (
              <div className="order-card" key={order.id}>
                <div className="order-header">
                  <div><strong>Order ID:</strong> {order.id}</div>
                  <div><strong>User:</strong> {order.username}</div>
                  <div><strong>Total:</strong> ₹{order.totalAmount}</div>
                  <div><strong>Date:</strong> {new Date(order.orderDate).toLocaleString()}</div>
                  <div><strong>Items:</strong> {order.orderItems?.length || 0}</div>
                  <button
                    onClick={() =>
                      setExpandedOrderId(expandedOrderId === order.id ? null : order.id)
                    }
                  >
                    {expandedOrderId === order.id ? 'Hide Items' : 'Show Items'}
                  </button>
                </div>

                {expandedOrderId === order.id && (
                  <div className="order-items">
                    <h4>Order Items</h4>
                    {order.orderItems.map((item) => (
                      <div className="order-item" key={item.orderItemId}>
                        <p><strong>Item ID:</strong> {item.orderItemId}</p>
                        <p><strong>Product ID:</strong> {item.productId}</p>
                        <p><strong>Name:</strong> {item.productName}</p>
                        <p><strong>Quantity:</strong> {item.quantity}</p>
                        <p><strong>Price:</strong> ₹{item.price}</p>
                      </div>
                    ))}
                  </div>
                )}
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  );
}

export default AdminOrders;
