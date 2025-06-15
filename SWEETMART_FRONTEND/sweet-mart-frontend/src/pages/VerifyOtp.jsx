import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { toast } from 'react-toastify';
// import '../css/verifyOtp.css';

function VerifyOtp() {
  const [email, setEmail] = useState('');
  const [otp, setOtp] = useState('');
  const navigate = useNavigate();

  const handleVerify = async () => {
    try {
      await axios.post('http://localhost:8080/auth/verify-otp', { email, otp });
      toast.success('OTP verified! You can now login.');
      navigate('/login');
    } catch (err) {
      toast.error(err.response?.data || 'OTP verification failed');
    }
  };

  return (
    <div className="verify-container">
      <h2>Verify OTP</h2>
      <input
        type="email"
        placeholder="Registered Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <input
        type="text"
        placeholder="Enter OTP"
        value={otp}
        onChange={(e) => setOtp(e.target.value)}
      />
      <button onClick={handleVerify}>Verify</button>
    </div>
  );
}

export default VerifyOtp;
