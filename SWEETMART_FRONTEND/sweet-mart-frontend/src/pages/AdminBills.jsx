import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../css/adminbill.css';

function AdminBills() {
  const [bills, setBills] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8080/orderbill/admin/allbills', {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => {
        setBills(res.data);
        setLoading(false);
      })
      .catch(() => {
        setError('Failed to fetch bills.');
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Loading all bills...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;

  return (
    <div className="admin-bills-container">
      <main className="admin-bills-content">
        <h2>All Bills (Admin)</h2>
        {bills.length === 0 ? (
          <p>No bills found.</p>
        ) : (
          <div className="bills-grid">
            {bills.map((bill) => (
              <div className="bill-card" key={bill.billId}>
                <div className="bill-header">
                  <div><strong>Bill ID:</strong> {bill.billId}</div>
                  <div><strong>User:</strong> {bill.username}</div>
                  <div><strong>Name:</strong> {bill.name}</div>
                  <div><strong>Shipping:</strong> {bill.shippingAddress}</div>
                  <div><strong>Total:</strong> ₹{bill.totalAmount}</div>
                  <div><strong>Date:</strong> {new Date(bill.createdDate).toLocaleString()}</div>
                  <div><strong>Order ID:</strong> {bill.sweetOrderId}</div>
                </div>
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  );
}

export default AdminBills;
