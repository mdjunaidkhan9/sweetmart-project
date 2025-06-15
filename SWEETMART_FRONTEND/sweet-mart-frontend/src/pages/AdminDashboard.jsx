import { useState } from 'react';
import UsersProducts from './UsersProducts';
import AdminCategory from './AdminCategory';
import AdminCustomers from './AdminCustomers';
import AdminOrders from './Adminorders';
import AdminBills from './AdminBills';
import AdminPayements from './AdminPayments';

import Users from './Users'; // You should create this component
import '../css/admindashboard.css';

function AdminDashboard() {
  const username = localStorage.getItem('username');
  const [activePage, setActivePage] = useState('users');

  const handleLogout = () => {
    localStorage.removeItem('username');
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    window.location.href = '/login';
  };

  return (
    <div className="dashboard-bg">
      {/* Top Navigation Bar */}
      <nav className="dashboard-navbar">
        <div className="dashboard-username">{username} (Admin)</div>
        <div className="dashboard-menu">
          <button
            className={activePage === 'users' ? 'dashboard-menu-active' : 'dashboard-menu-btn'}
            onClick={() => setActivePage('users')}
          >
            Users
          </button>
          <button
            className={activePage === 'products' ? 'dashboard-menu-active' : 'dashboard-menu-btn'}
            onClick={() => setActivePage('products')}
          >
            Products
          </button>
           <button
            className={activePage === 'categories' ? 'dashboard-menu-active' : 'dashboard-menu-btn'}
            onClick={() => setActivePage('categories')}
          >
            Categories
          </button>
          <button
            className={activePage === 'orders' ? 'dashboard-menu-active' : 'dashboard-menu-btn'}
            onClick={() => setActivePage('orders')}
          >
            Orders
          </button>
          <button
            className={activePage === 'bills' ? 'dashboard-menu-active' : 'dashboard-menu-btn'}
            onClick={() => setActivePage('bills')}
          >
            Bills
          </button>
          <button
            className={activePage === 'payment' ? 'dashboard-menu-active' : 'dashboard-menu-btn'}
            onClick={() => setActivePage('payment')}
          >
            Payments
          </button>
          <button
            className={activePage === 'customers' ? 'dashboard-menu-active' : 'dashboard-menu-btn'}
            onClick={() => setActivePage('customers')}
          >
            Customers
          </button>
        </div>
        <button className="dashboard-logout" onClick={handleLogout}>
          Logout
        </button>
      </nav>

      {/* Page Content */}
      <div className="dashboard-content">
        {activePage === 'users' && <Users />}
         {activePage === 'products' && <UsersProducts />}
         {activePage==='categories' && <AdminCategory/>}
         {activePage === 'orders' && <AdminOrders />}
         {activePage === 'bills' && <AdminBills/>}
        {activePage === 'payment' && <AdminPayements />}
         {activePage === 'customers' && <AdminCustomers />} 
      </div>
    </div>
  );
}

export default AdminDashboard;
