import { useEffect, useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import { loadStripe } from "@stripe/stripe-js";
import {
  Elements,
  CardElement,
  useStripe,
  useElements,
} from "@stripe/react-stripe-js";
import "react-toastify/dist/ReactToastify.css";
import "../css/bill.css";

const stripePromise = loadStripe(
  "pk_test_51RXh2zFMgAygdZsYrStGYxeDjHi2r7wPmcFOJyWyOofknKdJ8gqIxxpOotVSTgeJ8bBG70XDhonJphak2Luwnyx000rWv74TLz"
);

function CheckoutForm({ clientSecret, onPaymentSuccess }) {
  const stripe = useStripe();
  const elements = useElements();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!stripe || !elements) return;

    const result = await stripe.confirmCardPayment(clientSecret, {
      payment_method: {
        card: elements.getElement(CardElement),
      },
    });

    if (result.error) {
      toast.error(result.error.message);
    } else if (result.paymentIntent.status === "succeeded") {
      toast.success("Payment successful!");

      try {
        await axios.put(
          `http://localhost:8080/payment/update-status/${result.paymentIntent.id}`,
          {},
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        );
        toast.success("Payment status updated in server.");
        if (onPaymentSuccess) onPaymentSuccess();
      } catch (error) {
        toast.error("Failed to update payment status in server.");
      }
    }
  };

  return (
    <form onSubmit={handleSubmit} className="payment-form">
      <CardElement options={{ hidePostalCode: true }} />
      <button type="submit" disabled={!stripe}>
        Pay Now
      </button>
    </form>
  );
}

function Bill() {
  const [bill, setBill] = useState(null);
  const [clientSecret, setClientSecret] = useState(null);
  const [loading, setLoading] = useState(true);
  const [paymentDone, setPaymentDone] = useState(false);
  const token = localStorage.getItem("token");
  const username = localStorage.getItem("username");

  // Fetch bill on mount
  useEffect(() => {
    if (!username || !token) {
      toast.error("Not logged in.");
      setLoading(false);
      return;
    }

    axios
      .get(`http://localhost:8080/orderbill/user/getbill/${username}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => {
        setBill(res.data);
        setLoading(false);

        // Check payment flag for this bill
        const paidFlag = localStorage.getItem(
          `payment_done_${username}_${res.data.billId}`
        );
        setPaymentDone(paidFlag === "true");
      })
      .catch(() => {
        toast.error("Failed to fetch bill.");
        setLoading(false);
      });
  }, [username, token]);

  // Only called when user clicks Pay Now
  const initiatePayment = () => {
    axios
      .post(
        `http://localhost:8080/payment/user/${username}`,
        {},
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      )
      .then((res) => {
        setClientSecret(res.data.clientSecret);
        toast.info("Payment initiated. Please complete the payment below.");
      })
      .catch(() => {
        toast.error("Payment initiation failed.");
      });
  };

  if (loading) return <p>Loading bill...</p>;
  if (!bill) return <p>No bill found for {username}.</p>;

  return (
    <div className="bill-container">
      <ToastContainer />
      <h2>Your Bill</h2>
      <div className="bill-card">
        <p>
          <strong>Bill ID:</strong> {bill.billId}
        </p>
        <p>
          <strong>Sweet Order ID:</strong> {bill.sweetOrderId}
        </p>
        <p>
          <strong>Name:</strong> {bill.name}
        </p>
        <p>
          <strong>Username:</strong> {bill.username}
        </p>
        <p>
          <strong>Shipping Address:</strong> {bill.shippingAddress}
        </p>
        <p>
          <strong>Total Amount:</strong> ₹{bill.totalAmount}
        </p>
        <p>
          <strong>Created Date:</strong>{" "}
          {new Date(bill.createdDate).toLocaleString()}
        </p>
      </div>

      {/* Show Pay Now button only if payment not done and payment not initiated */}
      {!paymentDone && !clientSecret && (
        <button className="pay-button" onClick={initiatePayment}>
          Pay Now
        </button>
      )}

      {/* Show CheckoutForm only if payment not done and clientSecret is available */}
      {!paymentDone && clientSecret && (
        <Elements stripe={stripePromise} options={{ clientSecret }}>
          <CheckoutForm
            clientSecret={clientSecret}
            onPaymentSuccess={() => {
              // Set payment done for this bill
              localStorage.setItem(
                `payment_done_${username}_${bill.billId}`,
                "true"
              );
              setPaymentDone(true);
              setClientSecret(null);
            }}
          />
        </Elements>
      )}
      {/* After payment is done, nothing is shown here */}
    </div>
  );
}

export default Bill;
