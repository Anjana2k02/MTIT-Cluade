import axiosInstance from '../api/axiosInstance';

export const submitIntakeForm = (formData) => {
  return axiosInstance.post('/intake', formData);
};

export const getIntakeById = (serviceRecordId) => {
  return axiosInstance.get(`/intake/${serviceRecordId}`);
};

export const getAllIntakes = () => {
  return axiosInstance.get('/intake');
};

export const deleteServiceRecord = (id) => {
  return axiosInstance.delete(`/service-records/${id}`);
};

export const getAllCustomers = () => {
  return axiosInstance.get('/customers');
};

export const getVehiclesByCustomer = (customerId) => {
  return axiosInstance.get(`/vehicles/customer/${customerId}`);
};
