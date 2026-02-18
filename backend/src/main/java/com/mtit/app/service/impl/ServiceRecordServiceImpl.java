package com.mtit.app.service.impl;

import com.mtit.app.dto.intake.InternalDetailsRequest;
import com.mtit.app.dto.intake.ServiceDetailsRequest;
import com.mtit.app.dto.intake.ServicePartRequest;
import com.mtit.app.exception.ResourceNotFoundException;
import com.mtit.app.model.ServicePart;
import com.mtit.app.model.ServiceRecord;
import com.mtit.app.model.Vehicle;
import com.mtit.app.repository.ServiceRecordRepository;
import com.mtit.app.repository.VehicleRepository;
import com.mtit.app.service.ServiceRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceRecordServiceImpl implements ServiceRecordService {

    private final ServiceRecordRepository serviceRecordRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public ServiceRecord createServiceRecord(ServiceDetailsRequest serviceReq, InternalDetailsRequest internalReq, Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", vehicleId));

        ServiceRecord record = new ServiceRecord();
        record.setPartsRequired(new ArrayList<>());
        mapServiceDetails(record, serviceReq);
        mapInternalDetails(record, internalReq);

        record.setVehicle(vehicle);
        record.setWorkOrderNumber(generateWorkOrderNumber());
        record.setIntakeTimestamp(LocalDateTime.now());

        calculateCosts(record);
        mapParts(record, serviceReq.getPartsRequired());

        return serviceRecordRepository.save(record);
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceRecord getServiceRecordById(Long id) {
        return serviceRecordRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceRecord", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceRecord> getByVehicleId(Long vehicleId) {
        return serviceRecordRepository.findByVehicleIdWithDetails(vehicleId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceRecord> getAllServiceRecords() {
        return serviceRecordRepository.findAllWithDetails();
    }

    @Override
    public ServiceRecord updateServiceRecord(Long id, ServiceDetailsRequest serviceReq, InternalDetailsRequest internalReq) {
        ServiceRecord record = getServiceRecordById(id);
        mapServiceDetails(record, serviceReq);
        mapInternalDetails(record, internalReq);
        calculateCosts(record);

        if (record.getPartsRequired() == null) {
            record.setPartsRequired(new ArrayList<>());
        } else {
            record.getPartsRequired().clear();
        }
        mapParts(record, serviceReq.getPartsRequired());

        return serviceRecordRepository.save(record);
    }

    @Override
    public void softDeleteServiceRecord(Long id) {
        ServiceRecord record = getServiceRecordById(id);
        record.setDeleted(true);
        record.setDeletedAt(LocalDateTime.now());
        serviceRecordRepository.save(record);
    }

    private String generateWorkOrderNumber() {
        int currentYear = Year.now().getValue();
        Long count = serviceRecordRepository.countByIntakeYear(currentYear);
        return String.format("WO-%d-%04d", currentYear, count + 1);
    }

    private void calculateCosts(ServiceRecord record) {
        BigDecimal labor = record.getLaborCost() != null ? record.getLaborCost() : BigDecimal.ZERO;
        BigDecimal parts = record.getPartsCost() != null ? record.getPartsCost() : BigDecimal.ZERO;
        BigDecimal total = labor.add(parts);
        record.setTotalCost(total);

        BigDecimal vat = record.getVatPercentage() != null ? record.getVatPercentage() : BigDecimal.ZERO;
        BigDecimal discount = record.getDiscountPercentage() != null ? record.getDiscountPercentage() : BigDecimal.ZERO;

        BigDecimal vatAmount = total.multiply(vat).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal discountAmount = total.multiply(discount).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal finalAmount = total.add(vatAmount).subtract(discountAmount);

        record.setFinalPayableAmount(finalAmount.max(BigDecimal.ZERO));
    }

    private void mapServiceDetails(ServiceRecord record, ServiceDetailsRequest req) {
        record.setServiceType(req.getServiceType());
        record.setServicePriority(req.getServicePriority());
        record.setProblemDescription(req.getProblemDescription());
        record.setEstimatedBudget(req.getEstimatedBudget());
        record.setExpectedCompletionDate(req.getExpectedCompletionDate());
        record.setAssignedTechnician(req.getAssignedTechnician());
        record.setLaborCost(req.getLaborCost());
        record.setPartsCost(req.getPartsCost());
        record.setCustomerApprovalRequired(req.isCustomerApprovalRequired());
        record.setApprovalStatus(req.getApprovalStatus());
        record.setServiceAgreementConfirmed(req.isServiceAgreementConfirmed());
    }

    private void mapInternalDetails(ServiceRecord record, InternalDetailsRequest req) {
        if (req == null) return;
        record.setBranchLocation(req.getBranchLocation());
        record.setServiceAdvisor(req.getServiceAdvisor());
        record.setCustodian(req.getCustodian());
        record.setPaymentMethod(req.getPaymentMethod());
        record.setVatPercentage(req.getVatPercentage());
        record.setDiscountPercentage(req.getDiscountPercentage());
        record.setInternalNotes(req.getInternalNotes());
    }

    private void mapParts(ServiceRecord record, List<ServicePartRequest> partsReq) {
        if (partsReq == null || partsReq.isEmpty()) return;

        List<ServicePart> parts = new ArrayList<>();
        for (ServicePartRequest pr : partsReq) {
            ServicePart part = ServicePart.builder()
                    .partName(pr.getPartName())
                    .partNumber(pr.getPartNumber())
                    .quantity(pr.getQuantity())
                    .unitPrice(pr.getUnitPrice())
                    .serviceRecord(record)
                    .build();
            parts.add(part);
        }
        record.getPartsRequired().addAll(parts);
    }
}
