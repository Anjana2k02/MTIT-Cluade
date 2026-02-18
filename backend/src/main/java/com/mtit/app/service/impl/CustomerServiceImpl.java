package com.mtit.app.service.impl;

import com.mtit.app.dto.intake.CustomerRequest;
import com.mtit.app.exception.BusinessValidationException;
import com.mtit.app.exception.ResourceNotFoundException;
import com.mtit.app.model.Customer;
import com.mtit.app.model.enums.CustomerType;
import com.mtit.app.repository.CustomerRepository;
import com.mtit.app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(CustomerRequest request) {
        validateCorporateFields(request);

        if (customerRepository.existsByNicNumber(request.getNicNumber())) {
            throw new BusinessValidationException("nicNumber", "A customer with this NIC already exists");
        }

        Customer customer = mapToEntity(request);
        return customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
    }

    @Override
    public Customer findOrCreateByNic(CustomerRequest request) {
        validateCorporateFields(request);

        Optional<Customer> existing = customerRepository.findByNicNumber(request.getNicNumber());
        if (existing.isPresent()) {
            Customer customer = existing.get();
            updateEntityFromRequest(customer, request);
            return customerRepository.save(customer);
        }

        Customer customer = mapToEntity(request);
        return customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Long id, CustomerRequest request) {
        validateCorporateFields(request);

        Customer customer = getCustomerById(id);
        updateEntityFromRequest(customer, request);
        return customerRepository.save(customer);
    }

    @Override
    public void softDeleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customer.setDeleted(true);
        customer.setDeletedAt(LocalDateTime.now());
        customerRepository.save(customer);
    }

    private void validateCorporateFields(CustomerRequest request) {
        if (request.getCustomerType() == CustomerType.CORPORATE) {
            Map<String, String> errors = new HashMap<>();
            if (request.getCompanyRegistrationNumber() == null || request.getCompanyRegistrationNumber().isBlank()) {
                errors.put("companyRegistrationNumber", "Company registration number is required for corporate customers");
            }
            if (request.getTaxId() == null || request.getTaxId().isBlank()) {
                errors.put("taxId", "Tax identification number is required for corporate customers");
            }
            if (!errors.isEmpty()) {
                throw new BusinessValidationException(errors);
            }
        }
    }

    private Customer mapToEntity(CustomerRequest req) {
        return Customer.builder()
                .fullName(req.getFullName())
                .nicNumber(req.getNicNumber())
                .primaryContact(req.getPrimaryContact())
                .secondaryContact(req.getSecondaryContact())
                .email(req.getEmail())
                .street(req.getStreet())
                .city(req.getCity())
                .postalCode(req.getPostalCode())
                .customerType(req.getCustomerType())
                .companyRegistrationNumber(req.getCompanyRegistrationNumber())
                .taxId(req.getTaxId())
                .customerSince(req.getCustomerSince())
                .preferredContactMethod(req.getPreferredContactMethod())
                .emergencyContactName(req.getEmergencyContactName())
                .emergencyContactPhone(req.getEmergencyContactPhone())
                .build();
    }

    private void updateEntityFromRequest(Customer customer, CustomerRequest req) {
        customer.setFullName(req.getFullName());
        customer.setPrimaryContact(req.getPrimaryContact());
        customer.setSecondaryContact(req.getSecondaryContact());
        customer.setEmail(req.getEmail());
        customer.setStreet(req.getStreet());
        customer.setCity(req.getCity());
        customer.setPostalCode(req.getPostalCode());
        customer.setCustomerType(req.getCustomerType());
        customer.setCompanyRegistrationNumber(req.getCompanyRegistrationNumber());
        customer.setTaxId(req.getTaxId());
        customer.setCustomerSince(req.getCustomerSince());
        customer.setPreferredContactMethod(req.getPreferredContactMethod());
        customer.setEmergencyContactName(req.getEmergencyContactName());
        customer.setEmergencyContactPhone(req.getEmergencyContactPhone());
    }
}
