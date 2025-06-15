import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import { toast } from 'react-toastify';
import '../css/login.css';

function Login({ setAuth }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [selectedRole, setSelectedRole] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    // Client-side Validation
    if (!username.trim()) {
      toast.error("Username cannot be empty.");
      return;
    }

    if (!password.trim()) {
      toast.error("Password cannot be empty.");
      return;
    }

    if (!selectedRole) {
      toast.error("Please select a role (User/Admin)");
      return;
    }

    try {
      const response = await axios.post('http://localhost:8080/auth/login', {
        username,
        password,
      });

      const token = response.data;
      localStorage.setItem('token', token);
      localStorage.setItem('username', username);
      localStorage.setItem('role', selectedRole);

      setAuth({ token, role: selectedRole });

      toast.success("Login successful!");

      if (selectedRole === 'ADMIN') {
        navigate('/admin/dashboard');
      } else {
        navigate('/user/dashboard');
      }
    } catch (err) {
      toast.error("Invalid username or password!");
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleLogin}>
        <h2>Login</h2>

        <input
          type="text"
          placeholder="Enter username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />

        <input
          type="password"
          placeholder="Enter password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />

        <div className="radio-group">
          <label>
            <input
              type="radio"
              value="USER"
              checked={selectedRole === 'USER'}
              onChange={() => setSelectedRole('USER')}
            />
            User
          </label>
          <label>
            <input
              type="radio"
              value="ADMIN"
              checked={selectedRole === 'ADMIN'}
              onChange={() => setSelectedRole('ADMIN')}
            />
            Admin
          </label>
        </div>

        <button type="submit">Login</button>

        <p className="register-link">
          Not registered? <Link to="/register">Register here</Link>
        </p>
      </form>
    </div>
  );
}

export default Login;
