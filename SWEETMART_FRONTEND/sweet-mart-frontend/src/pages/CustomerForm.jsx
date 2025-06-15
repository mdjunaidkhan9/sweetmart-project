import { useState, useEffect } from 'react';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/customerform.css';

function CustomerForm() {
  // Read registered flag from localStorage on component mount
  const registeredFromStorage = localStorage.getItem('customerRegistered') === 'true';

  const [formData, setFormData] = useState({
    name: '',
    email: '',
    phone: '',
    shippingAddress: '',
  });
  const [customerData, setCustomerData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [registered, setRegistered] = useState(registeredFromStorage);

  const token = localStorage.getItem('token');
  const username = localStorage.getItem('username');

  const fetchCustomer = async () => {
    if (!token || !username) {
      toast.error('You are not logged in.');
      return;
    }
    setLoading(true);
    try {
      const res = await axios.get(`http://localhost:8080/customers/user/get/${username}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setCustomerData(res.data);
    } catch (err) {
      toast.error('Failed to fetch customer data.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCustomer();
    // eslint-disable-next-line
  }, []);

  const handleChange = (e) => {
    setFormData({...formData, [e.target.name]: e.target.value});
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!token || !username) {
      toast.error('You are not logged in.');
      return;
    }
    try {
      await axios.post(`http://localhost:8080/customers/user/add/${username}`, formData, {
        headers: { Authorization: `Bearer ${token}` }
      });
      toast.success('Customer registered successfully!');
      setRegistered(true);
      localStorage.setItem('customerRegistered', 'true'); // persist registration status
      fetchCustomer();
    } catch (err) {
      toast.error('Error registering customer.');
      console.error(err);
    }
  };

  if (loading) return <p>Loading...</p>;

  // Show form if user not registered OR customer data not fetched yet
  const showForm = !registered || !customerData;

  return (
    <div className="customer-form-container">
      <ToastContainer />
      <h2>Customer Registration</h2>

      {showForm && (
        <form className="customer-form" onSubmit={handleSubmit}>
          <input
            className="customer-form-input"
            type="text"
            name="name"
            placeholder="Full Name"
            onChange={handleChange}
            required
          />
          <input
            className="customer-form-input"
            type="email"
            name="email"
            placeholder="Email"
            onChange={handleChange}
            required
          />
          <input
            className="customer-form-input"
            type="text"
            name="phone"
            placeholder="Phone"
            onChange={handleChange}
            required
          />
          <input
            className="customer-form-input"
            type="text"
            name="shippingAddress"
            placeholder="Shipping Address"
            onChange={handleChange}
            required
          />
          <button className="customer-form-button" type="submit">
            Register
          </button>
        </form>
      )}

      {customerData && (
        <div className="customer-form-details">
          <h3>Registered Customer Info</h3>
          <p><strong>ID:</strong> {customerData.id}</p>
          <p><strong>Username:</strong> {customerData.username}</p>
          <p><strong>Name:</strong> {customerData.name}</p>
          <p><strong>Email:</strong> {customerData.email}</p>
          <p><strong>Phone:</strong> {customerData.phone}</p>
          <p><strong>Shipping Address:</strong> {customerData.shippingAddress}</p>
        </div>
      )}
    </div>
  );
}

export default CustomerForm;
