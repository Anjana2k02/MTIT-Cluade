package com.mtit.app.service.impl;

import com.mtit.app.dto.intake.VehicleRequest;
import com.mtit.app.exception.BusinessValidationException;
import com.mtit.app.exception.ResourceNotFoundException;
import com.mtit.app.model.Customer;
import com.mtit.app.model.Vehicle;
import com.mtit.app.repository.CustomerRepository;
import com.mtit.app.repository.VehicleRepository;
import com.mtit.app.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Vehicle createVehicle(VehicleRequest request, Long customerId) {
        validateManufacturingYear(request.getManufacturingYear());
        validateUniqueness(request, null);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        Vehicle vehicle = mapToEntity(request);
        vehicle.setCustomer(customer);
        return vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", id));
    }

    @Override
    public Vehicle findOrCreateByRegistration(VehicleRequest request, Long customerId) {
        validateManufacturingYear(request.getManufacturingYear());

        Optional<Vehicle> existing = vehicleRepository.findByRegistrationNumber(request.getRegistrationNumber());
        if (existing.isPresent()) {
            Vehicle vehicle = existing.get();
            updateEntityFromRequest(vehicle, request);
            return vehicleRepository.save(vehicle);
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        Vehicle vehicle = mapToEntity(request);
        vehicle.setCustomer(customer);
        return vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> getVehiclesByCustomerId(Long customerId) {
        return vehicleRepository.findByCustomerId(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle updateVehicle(Long id, VehicleRequest request) {
        validateManufacturingYear(request.getManufacturingYear());
        validateUniqueness(request, id);

        Vehicle vehicle = getVehicleById(id);
        updateEntityFromRequest(vehicle, request);
        return vehicleRepository.save(vehicle);
    }

    @Override
    public void softDeleteVehicle(Long id) {
        Vehicle vehicle = getVehicleById(id);
        vehicle.setDeleted(true);
        vehicle.setDeletedAt(LocalDateTime.now());
        vehicleRepository.save(vehicle);
    }

    private void validateManufacturingYear(Integer year) {
        if (year != null && year > Year.now().getValue()) {
            throw new BusinessValidationException("manufacturingYear", "Manufacturing year cannot be in the future");
        }
    }

    private void validateUniqueness(VehicleRequest request, Long excludeId) {
        Optional<Vehicle> byReg = vehicleRepository.findByRegistrationNumber(request.getRegistrationNumber());
        if (byReg.isPresent() && !byReg.get().getId().equals(excludeId)) {
            throw new BusinessValidationException("registrationNumber", "A vehicle with this registration number already exists");
        }
    }

    private Vehicle mapToEntity(VehicleRequest req) {
        return Vehicle.builder()
                .vehicleName(req.getVehicleName())
                .registrationNumber(req.getRegistrationNumber())
                .chassisNumber(req.getChassisNumber())
                .engineNumber(req.getEngineNumber())
                .brand(req.getBrand())
                .model(req.getModel())
                .manufacturingYear(req.getManufacturingYear())
                .vehicleType(req.getVehicleType())
                .fuelType(req.getFuelType())
                .transmissionType(req.getTransmissionType())
                .mileage(req.getMileage())
                .color(req.getColor())
                .insuranceProvider(req.getInsuranceProvider())
                .insuranceExpiryDate(req.getInsuranceExpiryDate())
                .warrantyStatus(req.getWarrantyStatus())
                .lastServiceDate(req.getLastServiceDate())
                .build();
    }

    private void updateEntityFromRequest(Vehicle vehicle, VehicleRequest req) {
        vehicle.setVehicleName(req.getVehicleName());
        vehicle.setChassisNumber(req.getChassisNumber());
        vehicle.setEngineNumber(req.getEngineNumber());
        vehicle.setBrand(req.getBrand());
        vehicle.setModel(req.getModel());
        vehicle.setManufacturingYear(req.getManufacturingYear());
        vehicle.setVehicleType(req.getVehicleType());
        vehicle.setFuelType(req.getFuelType());
        vehicle.setTransmissionType(req.getTransmissionType());
        vehicle.setMileage(req.getMileage());
        vehicle.setColor(req.getColor());
        vehicle.setInsuranceProvider(req.getInsuranceProvider());
        vehicle.setInsuranceExpiryDate(req.getInsuranceExpiryDate());
        vehicle.setWarrantyStatus(req.getWarrantyStatus());
        vehicle.setLastServiceDate(req.getLastServiceDate());
    }
}
