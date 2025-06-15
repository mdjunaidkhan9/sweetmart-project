// src/components/Payment.jsx
import { useEffect, useState } from "react";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../css/payment.css"; // Optional: Create a CSS file for styling

function Payment() {
  const [payment, setPayment] = useState(null);
  const [loading, setLoading] = useState(true);
  const token = localStorage.getItem("token");
  const username = localStorage.getItem("username");

  useEffect(() => {
    if (!username || !token) {
      toast.error("Unauthorized access.");
      setLoading(false);
      return;
    }

    axios
      .get(`http://localhost:8080/payment/user/get/${username}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => {
        setPayment(res.data);
        setLoading(false);
      })
      .catch(() => {
        toast.error("Failed to fetch payment details.");
        setLoading(false);
      });
  }, [username, token]);

  if (loading) return <p>Loading payment details...</p>;
  if (!payment) return <p>No payment details found for {username}.</p>;

  return (
    <div className="payment-container">
      <ToastContainer />
      <h2>Payment Details</h2>
      <div className="payment-card">
        <p><strong>Payment ID:</strong> {payment.paymentId}</p>
        <p><strong>Bill ID:</strong> {payment.billId}</p>
        <p><strong>Username:</strong> {payment.username}</p>
        <p><strong>Amount:</strong> ₹{payment.amount}</p>
        <p><strong>Status:</strong> {payment.status}</p>
      </div>
    </div>
  );
}

export default Payment;
