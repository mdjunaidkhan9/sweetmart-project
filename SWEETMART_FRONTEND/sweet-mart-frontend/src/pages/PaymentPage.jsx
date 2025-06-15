import { useEffect, useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import { useNavigate } from "react-router-dom";
import "react-toastify/dist/ReactToastify.css";

function PaymentPage() {
  const username = localStorage.getItem("username");
  const token = localStorage.getItem("token");
  const [payment, setPayment] = useState(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handlePayment = () => {
    setLoading(true);
    axios
      .post(`http://localhost:8080/payment/user/${username}`, null, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => {
        setPayment(res.data);
        toast.success("Payment successful!");
        setLoading(false);
      })
      .catch((err) => {
        toast.error("Payment failed!");
        setLoading(false);
      });
  };

  useEffect(() => {
    if (!username || !token) {
      toast.error("Unauthorized access.");
      navigate("/");
    }
  }, [username, token, navigate]);

  return (
    <div className="payment-container">
      <ToastContainer />
      <h2>Payment Page</h2>
      {!payment ? (
        <button onClick={handlePayment} disabled={loading}>
          {loading ? "Processing..." : "Pay Now"}
        </button>
      ) : (
        <div className="payment-card">
          <p><strong>Payment ID:</strong> {payment.paymentId}</p>
          <p><strong>Bill ID:</strong> {payment.billId}</p>
          <p><strong>Amount:</strong> ₹{payment.amount}</p>
          <p><strong>Status:</strong> {payment.status}</p>
        </div>
      )}
    </div>
  );
}

export default PaymentPage;
