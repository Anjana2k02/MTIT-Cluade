import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

function MainLayout({ children }) {
  const { isAuthenticated, user, logout } = useAuth();

  return (
    <div className="app-container">
      <header className="app-header">
        <div className="header-left">
          <h1><Link to="/" className="header-logo-link">MTIT App</Link></h1>
          {isAuthenticated && (
            <nav className="header-nav">
              <Link to="/" className="nav-link">Home</Link>
              <Link to="/records" className="nav-link">Service Records</Link>
              <Link to="/intake" className="nav-link">New Intake</Link>
            </nav>
          )}
        </div>
        {isAuthenticated && (
          <div className="header-right">
            <span className="header-user">{user?.username}</span>
            <button onClick={logout} className="logout-btn">Logout</button>
          </div>
        )}
      </header>
      <main className="app-main">{children}</main>
      <footer className="app-footer">
        <p>&copy; 2026 MTIT Assignment</p>
      </footer>
    </div>
  );
}

export default MainLayout;
