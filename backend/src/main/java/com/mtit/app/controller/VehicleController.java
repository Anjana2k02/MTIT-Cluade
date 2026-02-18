package com.mtit.app.controller;

import com.mtit.app.dto.intake.VehicleRequest;
import com.mtit.app.dto.intake.VehicleResponse;
import com.mtit.app.model.Vehicle;
import com.mtit.app.service.VehicleService;
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
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@Tag(name = "Vehicles", description = "Vehicle management endpoints")
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    @Operation(summary = "Get all vehicles")
    public ResponseEntity<List<VehicleResponse>> getAllVehicles() {
        List<VehicleResponse> vehicles = vehicleService.getAllVehicles().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get vehicle by ID")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(vehicleService.getVehicleById(id)));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get vehicles by customer ID")
    public ResponseEntity<List<VehicleResponse>> getByCustomer(@PathVariable Long customerId) {
        List<VehicleResponse> vehicles = vehicleService.getVehiclesByCustomerId(customerId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping
    @Operation(summary = "Create a new vehicle")
    public ResponseEntity<VehicleResponse> createVehicle(
            @Valid @RequestBody VehicleRequest request,
            @RequestParam Long customerId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(vehicleService.createVehicle(request, customerId)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing vehicle")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleRequest request) {
        return ResponseEntity.ok(toResponse(vehicleService.updateVehicle(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete a vehicle")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.softDeleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    private VehicleResponse toResponse(Vehicle v) {
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
}
