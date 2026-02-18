package com.mtit.app.controller;

import com.mtit.app.dto.intake.CustomerRequest;
import com.mtit.app.dto.intake.CustomerResponse;
import com.mtit.app.model.Customer;
import com.mtit.app.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "Customer management endpoints")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @Operation(summary = "Get all customers")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(customerService.getCustomerById(id)));
    }

    @PostMapping
    @Operation(summary = "Create a new customer")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(customerService.createCustomer(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing customer")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(toResponse(customerService.updateCustomer(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete a customer")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.softDeleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    private CustomerResponse toResponse(Customer c) {
        return CustomerResponse.builder()
                .id(c.getId())
                .fullName(c.getFullName())
                .nicNumber(c.getNicNumber())
                .primaryContact(c.getPrimaryContact())
                .secondaryContact(c.getSecondaryContact())
                .email(c.getEmail())
                .street(c.getStreet())
                .city(c.getCity())
                .postalCode(c.getPostalCode())
                .customerType(c.getCustomerType())
                .companyRegistrationNumber(c.getCompanyRegistrationNumber())
                .taxId(c.getTaxId())
                .customerSince(c.getCustomerSince())
                .preferredContactMethod(c.getPreferredContactMethod())
                .emergencyContactName(c.getEmergencyContactName())
                .emergencyContactPhone(c.getEmergencyContactPhone())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .createdBy(c.getCreatedBy())
                .build();
    }
}
