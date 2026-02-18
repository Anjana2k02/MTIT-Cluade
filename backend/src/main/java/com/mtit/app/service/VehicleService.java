package com.mtit.app.service;

import com.mtit.app.dto.intake.VehicleRequest;
import com.mtit.app.model.Vehicle;

import java.util.List;

public interface VehicleService {

    Vehicle createVehicle(VehicleRequest request, Long customerId);

    Vehicle getVehicleById(Long id);

    Vehicle findOrCreateByRegistration(VehicleRequest request, Long customerId);

    List<Vehicle> getVehiclesByCustomerId(Long customerId);

    List<Vehicle> getAllVehicles();

    Vehicle updateVehicle(Long id, VehicleRequest request);

    void softDeleteVehicle(Long id);
}
