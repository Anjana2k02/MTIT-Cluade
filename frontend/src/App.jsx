import { BrowserRouter } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import AppRoutes from './routes/AppRoutes';
import MainLayout from './layouts/MainLayout';

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <MainLayout>
          <AppRoutes />
        </MainLayout>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
