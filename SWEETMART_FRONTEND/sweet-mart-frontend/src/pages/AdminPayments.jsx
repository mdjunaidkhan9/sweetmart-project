import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../css/adminpayments.css';

function AdminPayments() {
  const [payments, setPayments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8080/payment/admin/getallpayement', {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => {
        setPayments(res.data);
        setLoading(false);
      })
      .catch(() => {
        setError('Failed to fetch payments.');
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Loading all payments...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;

  return (
    <div className="admin-payments-container">
      <main className="admin-payments-content">
        <h2>All Payments (Admin)</h2>
        {payments.length === 0 ? (
          <p>No payments found.</p>
        ) : (
          <div className="payments-grid">
            {payments.map((payment) => (
              <div className="payment-card" key={payment.paymentId}>
                <div className="payment-header">
                  <div><strong>Payment ID:</strong> {payment.paymentId}</div>
                  <div><strong>Username:</strong> {payment.username}</div>
                  <div><strong>Bill ID:</strong> {payment.billId}</div>
                  <div><strong>Amount:</strong> ₹{payment.amount}</div>
                  <div><strong>Status:</strong> {payment.status}</div>
                </div>
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  );
}

export default AdminPayments;
