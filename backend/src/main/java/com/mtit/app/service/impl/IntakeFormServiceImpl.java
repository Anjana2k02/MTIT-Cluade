package com.mtit.app.service.impl;

import com.mtit.app.dto.intake.*;
import com.mtit.app.model.Customer;
import com.mtit.app.model.ServicePart;
import com.mtit.app.model.ServiceRecord;
import com.mtit.app.model.Vehicle;
import com.mtit.app.service.CustomerService;
import com.mtit.app.service.IntakeFormService;
import com.mtit.app.service.ServiceRecordService;
import com.mtit.app.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IntakeFormServiceImpl implements IntakeFormService {

    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final ServiceRecordService serviceRecordService;

    @Override
    public IntakeFormResponse submitIntakeForm(IntakeFormRequest request) {
        Customer customer = customerService.findOrCreateByNic(request.getCustomer());
        Vehicle vehicle = vehicleService.findOrCreateByRegistration(request.getVehicle(), customer.getId());
        ServiceRecord record = serviceRecordService.createServiceRecord(
                request.getService(),
                request.getInternal(),
                vehicle.getId()
        );

        return buildResponse(record, customer, vehicle);
    }

    @Override
    @Transactional(readOnly = true)
    public IntakeFormResponse getIntakeFormByServiceRecordId(Long serviceRecordId) {
        ServiceRecord record = serviceRecordService.getServiceRecordById(serviceRecordId);
        Vehicle vehicle = record.getVehicle();
        Customer customer = vehicle.getCustomer();
        return buildResponse(record, customer, vehicle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IntakeFormResponse> getAllIntakeForms() {
        return serviceRecordService.getAllServiceRecords().stream()
                .map(record -> buildResponse(record, record.getVehicle().getCustomer(), record.getVehicle()))
                .collect(Collectors.toList());
    }

    private IntakeFormResponse buildResponse(ServiceRecord record, Customer customer, Vehicle vehicle) {
        return IntakeFormResponse.builder()
                .serviceRecordId(record.getId())
                .workOrderNumber(record.getWorkOrderNumber())
                .intakeTimestamp(record.getIntakeTimestamp())
                .customer(mapCustomerResponse(customer))
                .vehicle(mapVehicleResponse(vehicle))
                .service(mapServiceResponse(record))
                .internal(mapInternalResponse(record))
                .createdAt(record.getCreatedAt())
                .updatedAt(record.getUpdatedAt())
                .createdBy(record.getCreatedBy())
                .build();
    }

    private CustomerResponse mapCustomerResponse(Customer c) {
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

    private VehicleResponse mapVehicleResponse(Vehicle v) {
        return VehicleResponse.builder()
                .id(v.getId())
                .vehicleName(v.getVehicleName())
                .registrationNumber(v.getRegistrationNumber())
                .chassisNumber(v.getChassisNumber())
                .engineNumber(v.getEngineNumber())
                .brand(v.getBrand())
                .model(v.getModel())
                .manufacturingYear(v.getManufacturingYear())
                .vehicleType(v.getVehicleType())
                .fuelType(v.getFuelType())
                .transmissionType(v.getTransmissionType())
                .mileage(v.getMileage())
                .color(v.getColor())
                .insuranceProvider(v.getInsuranceProvider())
                .insuranceExpiryDate(v.getInsuranceExpiryDate())
                .warrantyStatus(v.getWarrantyStatus())
                .lastServiceDate(v.getLastServiceDate())
                .customerId(v.getCustomer().getId())
                .createdAt(v.getCreatedAt())
                .updatedAt(v.getUpdatedAt())
                .createdBy(v.getCreatedBy())
                .build();
    }

    private ServiceDetailsResponse mapServiceResponse(ServiceRecord r) {
        List<ServicePartResponse> parts = r.getPartsRequired().stream()
                .map(this::mapPartResponse)
                .collect(Collectors.toList());

        return ServiceDetailsResponse.builder()
                .serviceType(r.getServiceType())
                .servicePriority(r.getServicePriority())
                .problemDescription(r.getProblemDescription())
                .estimatedBudget(r.getEstimatedBudget())
                .expectedCompletionDate(r.getExpectedCompletionDate())
                .assignedTechnician(r.getAssignedTechnician())
                .partsRequired(parts)
                .laborCost(r.getLaborCost())
                .partsCost(r.getPartsCost())
                .totalCost(r.getTotalCost())
                .customerApprovalRequired(r.isCustomerApprovalRequired())
                .approvalStatus(r.getApprovalStatus())
                .serviceAgreementConfirmed(r.isServiceAgreementConfirmed())
                .build();
    }

    private ServicePartResponse mapPartResponse(ServicePart p) {
        return ServicePartResponse.builder()
                .id(p.getId())
                .partName(p.getPartName())
                .partNumber(p.getPartNumber())
                .quantity(p.getQuantity())
                .unitPrice(p.getUnitPrice())
                .build();
    }

    private InternalDetailsResponse mapInternalResponse(ServiceRecord r) {
        return InternalDetailsResponse.builder()
                .branchLocation(r.getBranchLocation())
                .serviceAdvisor(r.getServiceAdvisor())
                .custodian(r.getCustodian())
                .workOrderNumber(r.getWorkOrderNumber())
                .intakeTimestamp(r.getIntakeTimestamp())
                .paymentMethod(r.getPaymentMethod())
                .vatPercentage(r.getVatPercentage())
                .discountPercentage(r.getDiscountPercentage())
                .finalPayableAmount(r.getFinalPayableAmount())
                .internalNotes(r.getInternalNotes())
                .build();
    }
}
