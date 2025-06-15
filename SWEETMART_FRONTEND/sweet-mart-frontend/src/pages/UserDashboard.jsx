import { useState } from 'react';
import Products from './Products';
import Categories from './Categories';
import Cart from './Cart';
import Orders from './Orders';
import CustomerForm from './CustomerForm';
import Bill from './Bill';  // Import Bill component
import '../css/userdashboard.css';
import MyBills from './MyBills';
import Payment from './Payment';
import MyPayments from './MyPayments';
function UserDashboard() {
  const username = localStorage.getItem('username');
  const [activePage, setActivePage] = useState('products');

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
        <div className="dashboard-username">{username}</div>
        <div className="dashboard-menu">
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
            className={activePage === 'cart' ? 'dashboard-menu-active' : 'dashboard-menu-btn'}
            onClick={() => setActivePage('cart')}
          >
            Cart
          </button>
          <button
            className={activePage === 'orders' ? 'dashboard-menu-active' : 'dashboard-menu-btn'}
            onClick={() => setActivePage('orders')}
          >
            Orders
          </button>
          <button
            className={activePage === 'register' ? 'dashboard-menu-active' : 'dashboard-menu-btn'}
            onClick={() => setActivePage('register')}
          >
            Register
          </button>
          <button
            className={activePage === 'bill' ? 'dashboard-menu-active' : 'dashboard-menu-btn'}
            onClick={() => setActivePage('bill')}
          >
            Bill
          </button>
          <button
            className={activePage === 'mybills' ? 'dashboard-menu-active' : 'dashboard-menu-btn'}
            onClick={() => setActivePage('mybills')}
          >
            My Bills
          </button>
           <button
            className={activePage === 'mypay' ? 'dashboard-menu-active' : 'dashboard-menu-btn'}
            onClick={() => setActivePage('mypay')}
          >
            My Pays
          </button>
          <button
  className={activePage === 'payment' ? 'dashboard-menu-active' : 'dashboard-menu-btn'}
  onClick={() => setActivePage('payment')}
>
  Payment
</button>

        </div>
        <button className="dashboard-logout" onClick={handleLogout}>
          Logout
        </button>
      </nav>

      {/* Page Content */}
      <div className="dashboard-content">
        {activePage === 'products' && <Products />}
        {activePage === 'categories' && <Categories />}
        {activePage === 'cart' && <Cart />}
        {activePage === 'orders' && <Orders />}
        {activePage === 'register' && <CustomerForm />}
        {activePage === 'bill' && <Bill />}  {/* Show Bill component */}
        {activePage === 'payment' && <Payment />}
        {activePage === 'mybills' && <MyBills />}
        {activePage === 'mypay' && <MyPayments />}
      </div>
    </div>
  );
}

export default UserDashboard;
