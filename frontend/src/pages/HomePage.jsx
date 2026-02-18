import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { testSecure } from '../services/authService';

function HomePage() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [secureData, setSecureData] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleTestSecure = async () => {
    setLoading(true);
    try {
      const response = await testSecure();
      setSecureData(response.data);
    } catch {
      setSecureData({ error: 'Failed to access protected endpoint' });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="home-page">
      <div className="welcome-card">
        <h2>Welcome, {user?.username}!</h2>
        <p className="user-info">Email: {user?.email}</p>
        <p className="user-info">Role: {user?.role}</p>
      </div>

      <div className="action-cards">
        <div className="action-card" onClick={() => navigate('/intake')}>
          <div className="action-icon">+</div>
          <h3>New Service Intake</h3>
          <p>Create a new service intake form for a customer vehicle</p>
        </div>
        <div className="action-card" onClick={() => navigate('/records')}>
          <div className="action-icon">&#9776;</div>
          <h3>Service Records</h3>
          <p>View and manage all service intake records</p>
        </div>
      </div>

      <div className="secure-test">
        <h3>Test Protected Endpoint</h3>
        <button onClick={handleTestSecure} className="test-btn" disabled={loading}>
          {loading ? 'Testing...' : 'Call /api/test/secure'}
        </button>
        {secureData && (
          <pre className="response-block">{JSON.stringify(secureData, null, 2)}</pre>
        )}
      </div>
    </div>
  );
}

export default HomePage;
