import axiosInstance from '../api/axiosInstance';

export const loginUser = (credentials) => {
  return axiosInstance.post('/auth/login', credentials);
};

export const registerUser = (userData) => {
  return axiosInstance.post('/auth/register', userData);
};

export const testSecure = () => {
  return axiosInstance.get('/test/secure');
};
