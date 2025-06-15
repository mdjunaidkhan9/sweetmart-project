// import { useState } from 'react';
// import { useNavigate, Link } from 'react-router-dom';
// import axios from 'axios';
// import { toast } from 'react-toastify';
// import '../css/register.css';

// function Register() {
//   const [formData, setFormData] = useState({
//     username: '',
//     password: '',
//     userEmail: '',
//     role: '',
//   });

//   const [errors, setErrors] = useState({});
//   const navigate = useNavigate();

//   const handleChange = (e) => {
//     const { name, value } = e.target;
//     setFormData(prev => ({
//       ...prev,
//       [name]: value,
//     }));
//     setErrors(prev => ({
//       ...prev,
//       [name]: '',
//     }));
//   };

//   // 🔍 Validation function
//   const validate = () => {
//     const newErrors = {};
//     if (!formData.username.trim()) {
//       newErrors.username = 'Username is required';
//     }
//     if (!formData.password || formData.password.length < 6) {
//       newErrors.password = 'Password must be at least 6 characters';
//     }
//     if (!formData.userEmail || !/\S+@\S+\.\S+/.test(formData.userEmail)) {
//       newErrors.userEmail = 'Enter a valid email address';
//     }
//     if (!formData.role || !['USER', 'ADMIN'].includes(formData.role.toUpperCase())) {
//       newErrors.role = 'Role must be USER or ADMIN';
//     }
//     return newErrors;
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     const validationErrors = validate();
//     if (Object.keys(validationErrors).length > 0) {
//       setErrors(validationErrors);
//       return;
//     }

//     try {
//       await axios.post('http://localhost:8080/auth/register', formData);
//       toast.success('Registered successfully!');
//       navigate('/login');
//     } catch (err) {
//       toast.error(err.response?.data || 'Registration failed');
//     }
//   };

//   return (
//     <div className="register-container">
//       <form onSubmit={handleSubmit} className="register-form">
//         <h2>Register</h2>

//         <input
//           type="text"
//           name="username"
//           placeholder="Username"
//           value={formData.username}
//           onChange={handleChange}
//         />
//         {errors.username && <p className="error">{errors.username}</p>}

//         <input
//           type="password"
//           name="password"
//           placeholder="Password"
//           value={formData.password}
//           onChange={handleChange}
//         />
//         {errors.password && <p className="error">{errors.password}</p>}

//         <input
//           type="email"
//           name="userEmail"
//           placeholder="Email"
//           value={formData.userEmail}
//           onChange={handleChange}
//         />
//         {errors.userEmail && <p className="error">{errors.userEmail}</p>}

//         <input
//           type="text"
//           name="role"
//           placeholder="Enter role (e.g., USER or ADMIN)"
//           value={formData.role}
//           onChange={handleChange}
//         />
//         {errors.role && <p className="error">{errors.role}</p>}

//         <button type="submit">Register</button>

//         <p className="login-link">
//           Already have an account? <Link to="/login">Login here</Link>
//         </p>
//       </form>
//     </div>
//   );
// }

// export default Register;
import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import { toast } from 'react-toastify';
import '../css/register.css';

function Register() {
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    userEmail: '',
    role: '',
  });

  const [otp, setOtp] = useState('');
  const [isOtpSent, setIsOtpSent] = useState(false);
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
    setErrors((prev) => ({
      ...prev,
      [name]: '',
    }));
  };

  const validate = () => {
    const newErrors = {};
    if (!formData.username.trim()) {
      newErrors.username = 'Username is required';
    }
    if (!formData.password || formData.password.length < 6) {
      newErrors.password = 'Password must be at least 6 characters';
    }
    if (!formData.userEmail || !/\S+@\S+\.\S+/.test(formData.userEmail)) {
      newErrors.userEmail = 'Enter a valid email address';
    }
    if (!formData.role || !['USER', 'ADMIN'].includes(formData.role.toUpperCase())) {
      newErrors.role = 'Role must be USER or ADMIN';
    }
    return newErrors;
  };

  // Step 1: Send OTP to userEmail
  const handleSendOtp = async (e) => {
    e.preventDefault();
    const validationErrors = validate();
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }

    try {
      await axios.post('http://localhost:8080/auth/register/request-otp', formData);
      setIsOtpSent(true);
      toast.success('OTP sent to your email!');
    } catch (err) {
      toast.error(err.response?.data || 'Failed to send OTP');
    }
  };

  // Step 2: Verify OTP and complete registration
  const handleVerifyOtp = async (e) => {
    e.preventDefault();

    if (!otp || otp.length !== 6) {
      toast.error('Please enter a valid 6-digit OTP');
      return;
    }

    try {
      await axios.post('http://localhost:8080/auth/register/verify-otp', null, {
        params: {
          username: formData.username,
          otp: otp,
        },
      });
      toast.success('Registration complete!');
      navigate('/login');
    } catch (err) {
      toast.error(err.response?.data || 'OTP verification failed');
    }
  };

  return (
    <div className="register-container">
      {!isOtpSent ? (
        <form onSubmit={handleSendOtp} className="register-form">
          <h2>Register</h2>

          <input
            type="text"
            name="username"
            placeholder="Username"
            value={formData.username}
            onChange={handleChange}
          />
          {errors.username && <p className="error">{errors.username}</p>}

          <input
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
          />
          {errors.password && <p className="error">{errors.password}</p>}

          <input
            type="email"
            name="userEmail"
            placeholder="Email"
            value={formData.userEmail}
            onChange={handleChange}
          />
          {errors.userEmail && <p className="error">{errors.userEmail}</p>}

          <input
            type="text"
            name="role"
            placeholder="Enter role (e.g., USER or ADMIN)"
            value={formData.role}
            onChange={handleChange}
          />
          {errors.role && <p className="error">{errors.role}</p>}

          <button type="submit">Send OTP</button>

          <p className="login-link">
            Already have an account? <Link to="/login">Login here</Link>
          </p>
        </form>
      ) : (
        <form onSubmit={handleVerifyOtp} className="register-form">
          <h2>Enter OTP</h2>
          <p>OTP sent to your email: {formData.userEmail}</p>
          <input
            type="text"
            placeholder="Enter 6-digit OTP"
            value={otp}
            onChange={(e) => setOtp(e.target.value)}
          />
          <button type="submit">Verify OTP</button>
        </form>
      )}
    </div>
  );
}

export default Register;
