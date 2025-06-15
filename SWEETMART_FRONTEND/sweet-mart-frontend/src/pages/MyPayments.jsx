import React, { useEffect, useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../css/orders.css"; // Use your preferred CSS

const MyPayments = () => {
  const [payments, setPayments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const token = localStorage.getItem("token");
  const username = localStorage.getItem("username");

  useEffect(() => {
    if (!token || !username) {
      setError("You are not logged in.");
      setLoading(false);
      return;
    }

    const fetchPayments = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/payment/user/allpayments/${username}`,
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );
        setPayments(response.data);
      } catch (err) {
        setError("Failed to fetch payments.");
      } finally {
        setLoading(false);
      }
    };

    fetchPayments();
  }, [token, username]);

  if (loading) return <p>Loading payments...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;
  if (!payments || payments.length === 0) return <p>No payments found.</p>;

  return (
    <div className="orders-container">
      <ToastContainer position="top-right" autoClose={3000} />
      <h2>My Payments</h2>
      {payments.map((payment) => (
        <div className="order-card" key={payment.paymentId}>
          <div className="order-header">
            <span>Payment ID: {payment.paymentId}</span>
            <span>Bill ID: {payment.billId}</span>
            <span>Amount: ₹{payment.amount}</span>
            <span>Status: {payment.status}</span>
          </div>
        </div>
      ))}
    </div>
  );
};

export default MyPayments;
