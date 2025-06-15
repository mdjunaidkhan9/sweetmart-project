import { useState, useEffect } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/admincustomers.css'; // 👈 Updated to point to admin-specific CSS

const API_BASE = 'http://localhost:8080/customers/admin';

function AdminCustomers() {
  const token = localStorage.getItem('token');
  const headers = { Authorization: `Bearer ${token}` };

  const [view, setView] = useState('all');
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchAll = async () => {
    setLoading(true);
    try {
      const res = await axios.get(`${API_BASE}/all`, { headers });
      setCustomers(res.data);
    } catch {
      toast.error('Failed to fetch customers');
    }
    setLoading(false);
  };

  const fetchSortedByName = async () => {
    setLoading(true);
    try {
      const res = await axios.get(`${API_BASE}/sortbyname`, { headers });
      setCustomers(res.data);
    } catch {
      toast.error('Failed to sort by name');
    }
    setLoading(false);
  };

  const fetchSortedByEmail = async () => {
    setLoading(true);
    try {
      const res = await axios.get(`${API_BASE}/sortbyemail`, { headers });
      setCustomers(res.data);
    } catch {
      toast.error('Failed to sort by email');
    }
    setLoading(false);
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this customer?')) return;
    try {
      await axios.delete(`${API_BASE}/delete/${id}`, { headers });
      toast.success('Customer deleted');
      setCustomers((prev) => prev.filter((c) => c.id !== id));
    } catch {
      toast.error('Delete failed');
    }
  };

  useEffect(() => {
    if (view === 'all') fetchAll();
    else if (view === 'sortByName') fetchSortedByName();
    else if (view === 'sortByEmail') fetchSortedByEmail();
  }, [view]);

  return (
    <div className="user-products-container">
      <div className="user-products-sidebar">
        <h3>Admin Customers</h3>
        <button onClick={() => setView('all')}>View All</button>
        <button onClick={() => setView('sortByName')}>Sort by Name</button>
        <button onClick={() => setView('sortByEmail')}>Sort by Email</button>
      </div>
      <div className="user-products-content">
        <ToastContainer />
        <h2>Customers</h2>
        {loading ? (
          <p>Loading...</p>
        ) : (
          <div className="user-products-grid">
            {customers.map((customer) => (
              <div className="user-product-card" key={customer.id}>
                <h3>{customer.name}</h3>
                <p><strong>ID:</strong> {customer.id}</p>
                <p><strong>Username:</strong> {customer.username}</p>
                <p><strong>Email:</strong> {customer.email}</p>
                <p><strong>Phone:</strong> {customer.phone}</p>
                <p><strong>Shipping Address:</strong> {customer.shippingAddress}</p>
                <button onClick={() => handleDelete(customer.id)}>Delete</button>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default AdminCustomers;
