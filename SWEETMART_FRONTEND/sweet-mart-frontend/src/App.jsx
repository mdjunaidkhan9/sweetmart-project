import { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, Link, useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import Login from './pages/Login';
import UserDashboard from './pages/UserDashboard';
import AdminDashboard from './pages/AdminDashboard';
import Register from './pages/Register';
import Products from './pages/Products';
import Cart from './pages/Cart';
import Bill from './pages/Bill';
import PaymentPage from './pages/PaymentPage';
import 'react-toastify/dist/ReactToastify.css';

function App() {
  const [auth, setAuth] = useState({
    token: localStorage.getItem('token'),
    role: localStorage.getItem('role')
  });

  const logout = () => {
    localStorage.clear();
    setAuth({ token: null, role: null });
    toast.success("Logged out successfully!");
  };

  const ProtectedRoute = ({ children, allowedRole }) => {
    if (!auth.token || auth.role !== allowedRole) {
      toast.warning("Unauthorized access. Please login.");
      return <Navigate to="/login" />;
    }
    return children;
  };

  const Navbar = () => (
    <nav style={{ padding: "10px", background: "#f0f0f0" }}>
      {auth.token && auth.role === 'USER' && (
        <>
          <Link to="/user/dashboard" style={{ marginRight: "10px" }}>Dashboard</Link>
          <Link to="/products" style={{ marginRight: "10px" }}>Products</Link>
          <Link to="/cart" style={{ marginRight: "10px" }}>Cart</Link>
        </>
      )}
      {auth.token && auth.role === 'ADMIN' && (
        <>
          <Link to="/admin/dashboard" style={{ marginRight: "10px" }}>Admin Dashboard</Link>
        </>
      )}
      {auth.token ? (
        <button onClick={logout} style={{ marginLeft: "10px" }}>Logout</button>
      ) : (
        <Link to="/login" style={{ marginLeft: "10px" }}>Login</Link>
      )}
    </nav>
  );

  // Keep auth in sync with localStorage
  useEffect(() => {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');
    setAuth({ token, role });
  }, []);

  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path='/register' element={<Register />} />
        <Route path="/login" element={<Login setAuth={setAuth} />} />
        
         <Route path="/bill" element={<Bill />} />
        <Route path="/payment" element={<PaymentPage />} />
        <Route path="/user/dashboard" element={
          <ProtectedRoute allowedRole="USER">
            <UserDashboard />
          </ProtectedRoute>
        } />
        <Route path="/admin/dashboard" element={
          <ProtectedRoute allowedRole="ADMIN">
            <AdminDashboard />
          </ProtectedRoute>
        } />
        <Route path="/products" element={
          <ProtectedRoute allowedRole="USER">
            <Products />
          </ProtectedRoute>
        } />
        <Route path="/cart" element={
          <ProtectedRoute allowedRole="USER">
            <Cart />
          </ProtectedRoute>
        } />
        <Route path="*" element={<Navigate to="/login" />} />
      </Routes>
      <ToastContainer position="top-right" autoClose={3000} />
    </Router>
  );
}

export default App;
