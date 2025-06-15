import { useEffect, useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import CustomerForm from "./CustomerForm";
import "../css/orders.css";

function Orders() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showCustomerForm, setShowCustomerForm] = useState(false);
  const [billGenerated, setBillGenerated] = useState(false);

  const token = localStorage.getItem("token");
  const username = localStorage.getItem("username");

  const getOrderId = (order) => order.orderId || order.id;

  // ✅ Async fetchOrders function
  const fetchOrders = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/orders/user/${username}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setOrders(response.data);
      setLoading(false);

      if (response.data && response.data.length > 0) {
        const latestOrder = response.data[response.data.length - 1];
        const billFlag = localStorage.getItem(`billGenerated_${getOrderId(latestOrder)}`);
        setBillGenerated(billFlag === "true");
      } else {
        setBillGenerated(false);
      }
    } catch (err) {
      setError("Failed to fetch orders.");
      setLoading(false);
      toast.error("Failed to fetch orders.");
    }
  };

  useEffect(() => {
    if (!token || !username) {
      setError("You are not logged in.");
      setLoading(false);
      toast.error("You are not logged in.");
      return;
    }
    fetchOrders();
  }, [token, username]);

  const handleOrderPlaced = () => {
    setShowCustomerForm(false);
    fetchOrders();
  };

  const generateBill = async () => {
    if (orders.length === 0) return;

    const latestOrder = orders[orders.length - 1];
    try {
      const response = await axios.post(
        `http://localhost:8080/orderbill/user/${username}/orderbill`,
        null,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      toast.success(`Bill generated! Bill ID: ${response.data.billId}`);
      setBillGenerated(true);
      localStorage.setItem(`billGenerated_${getOrderId(latestOrder)}`, "true");
    } catch (err) {
      toast.error("Failed to generate bill.");
    }
  };

  const viewMyBill = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/orderbill/user/getbill/${username}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      const bill = response.data;
      alert(
        `Bill ID: ${bill.billId}\n` +
          `Name: ${bill.name}\n` +
          `Username: ${bill.username}\n` +
          `Shipping Address: ${bill.shippingAddress}\n` +
          `Total Amount: ₹${bill.totalAmount}\n` +
          `Date: ${new Date(bill.createdDate).toLocaleString()}`
      );
    } catch (err) {
      toast.error("Failed to fetch bill.");
    }
  };

  const deleteAllOrders = async () => {
    try {
      await axios.delete(`http://localhost:8080/orders/user/${username}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      toast.success("All orders deleted");
      setBillGenerated(false);
      orders.forEach((order) =>
        localStorage.removeItem(`billGenerated_${getOrderId(order)}`)
      );
      fetchOrders();
    } catch (err) {
      toast.error("Failed to delete orders");
    }
  };

  if (loading) return <p>Loading orders...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  return (
    <div className="orders-container">
      <ToastContainer />
      <h2>{username}'s Orders</h2>

      {orders.length === 0 ? (
        <p className="orders-empty">You have no orders yet.</p>
      ) : (
        <>
          {orders.map((order) => (
            <div className="order-card" key={getOrderId(order)}>
              <div className="order-header">
                <span>Order ID: {getOrderId(order)}</span>
                <span>Date: {new Date(order.orderDate).toLocaleString()}</span>
                <span>Total: ₹{order.totalAmount}</span>
                <span>Status: {order.paymentStatus || "Pending"}</span>
              </div>

              <div className="order-items">
                {order.orderItems.map((item) => (
                  <div key={item.id} className="order-item">
                    <span>{item.productName}</span> x <span>{item.quantity}</span> @ ₹
                    <span>{item.price}</span>
                  </div>
                ))}
              </div>
            </div>
          ))}

          <div className="orders-actions">
            {!billGenerated && orders.length > 0 && (
              <button onClick={generateBill} className="orders-pay-btn">
                Generate Bill
              </button>
            )}
            <button onClick={viewMyBill} className="view-bill-btn">
              View My Bill
            </button>
            <button onClick={deleteAllOrders} className="orders-delete-btn">
              Delete All Orders
            </button>
          </div>
        </>
      )}

      {showCustomerForm && (
        <div className="modal-overlay">
          <div className="modal-content">
            <CustomerForm onClose={handleOrderPlaced} />
          </div>
        </div>
      )}
    </div>
  );
}

export default Orders;
