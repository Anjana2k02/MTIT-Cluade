package com.mtit.app.controller;

import com.mtit.app.dto.intake.*;
import com.mtit.app.model.ServicePart;
import com.mtit.app.model.ServiceRecord;
import com.mtit.app.service.ServiceRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/service-records")
@RequiredArgsConstructor
@Tag(name = "Service Records", description = "Service record management endpoints")
public class ServiceRecordController {

    private final ServiceRecordService serviceRecordService;

    @GetMapping
    @Operation(summary = "Get all service records")
    public ResponseEntity<List<IntakeFormResponse>> getAllServiceRecords() {
        List<IntakeFormResponse> records = serviceRecordService.getAllServiceRecords().stream()
                .map(this::toFullResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(records);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get service record by ID")
    public ResponseEntity<IntakeFormResponse> getServiceRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(toFullResponse(serviceRecordService.getServiceRecordById(id)));
    }

    @GetMapping("/vehicle/{vehicleId}")
    @Operation(summary = "Get service records by vehicle ID")
    public ResponseEntity<List<IntakeFormResponse>> getByVehicle(@PathVariable Long vehicleId) {
        List<IntakeFormResponse> records = serviceRecordService.getByVehicleId(vehicleId).stream()
                .map(this::toFullResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(records);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a service record")
    public ResponseEntity<IntakeFormResponse> updateServiceRecord(
            @PathVariable Long id,
            @Valid @RequestBody ServiceRecordUpdateRequest request) {
        return ResponseEntity.ok(toFullResponse(
                serviceRecordService.updateServiceRecord(id, request.getService(), request.getInternal())
        ));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete a service record")
    public ResponseEntity<Void> deleteServiceRecord(@PathVariable Long id) {
        serviceRecordService.softDeleteServiceRecord(id);
        return ResponseEntity.noContent().build();
    }

    private IntakeFormResponse toFullResponse(ServiceRecord r) {
        List<ServicePartResponse> parts = r.getPartsRequired().stream()
                .map(this::toPartResponse)
                .collect(Collectors.toList());

        return IntakeFormResponse.builder()
                .serviceRecordId(r.getId())
                .workOrderNumber(r.getWorkOrderNumber())
                .intakeTimestamp(r.getIntakeTimestamp())
                .service(ServiceDetailsResponse.builder()
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
                        .build())
                .internal(InternalDetailsResponse.builder()
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
                        .build())
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .createdBy(r.getCreatedBy())
                .build();
    }

    private ServicePartResponse toPartResponse(ServicePart p) {
        return ServicePartResponse.builder()
                .id(p.getId())
                .partName(p.getPartName())
                .partNumber(p.getPartNumber())
                .quantity(p.getQuantity())
                .unitPrice(p.getUnitPrice())
                .build();
    }
}
