package com.mtit.app.service;

import com.mtit.app.dto.intake.CustomerRequest;
import com.mtit.app.model.Customer;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(CustomerRequest request);

    Customer getCustomerById(Long id);

    Customer findOrCreateByNic(CustomerRequest request);

    List<Customer> getAllCustomers();

    Customer updateCustomer(Long id, CustomerRequest request);

    void softDeleteCustomer(Long id);
}
