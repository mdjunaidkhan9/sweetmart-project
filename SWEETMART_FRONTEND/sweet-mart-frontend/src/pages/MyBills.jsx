import React, { useEffect, useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../css/bill.css";

const MyBills = () => {
  const [bills, setBills] = useState([]);
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

    const fetchBills = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/orderbill/user/allbills/${username}`,
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );
        setBills(response.data);
      } catch (err) {
        setError("Failed to fetch bills.");
      } finally {
        setLoading(false);
      }
    };

    fetchBills();
  }, [token, username]);

  if (loading) return <p>Loading bills...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;
  if (!bills || bills.length === 0) return <p>No bills found.</p>;

  return (
    <div className="orders-container">
      <ToastContainer position="top-right" autoClose={3000} />
      <h2>My Bills</h2>
      {bills.map((bill) => (
        <div className="order-card" key={bill.billId}>
          <div className="order-header">
            <span>Bill ID: {bill.billId}</span>
            <span>Date: {new Date(bill.createdDate).toLocaleString()}</span>
            <span>Total: ₹{bill.totalAmount}</span>
          </div>
          <div>
            <span>Order ID: {bill.sweetOrderId}</span>
            <span>Name: {bill.name}</span>
            <span>Shipping Address: {bill.shippingAddress}</span>
          </div>
        </div>
      ))}
    </div>
  );
};

export default MyBills;
