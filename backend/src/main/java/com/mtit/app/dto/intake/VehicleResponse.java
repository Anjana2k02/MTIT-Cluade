package com.mtit.app.dto.intake;

import com.mtit.app.model.enums.FuelType;
import com.mtit.app.model.enums.TransmissionType;
import com.mtit.app.model.enums.VehicleType;
import com.mtit.app.model.enums.WarrantyStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {

    private Long id;
    private String vehicleName;
    private String registrationNumber;
    private String chassisNumber;
    private String engineNumber;
    private String brand;
    private String model;
    private Integer manufacturingYear;
    private VehicleType vehicleType;
    private FuelType fuelType;
    private TransmissionType transmissionType;
    private Double mileage;
    private String color;
    private String insuranceProvider;
    private LocalDate insuranceExpiryDate;
    private WarrantyStatus warrantyStatus;
    private LocalDate lastServiceDate;
    private Long customerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
}
