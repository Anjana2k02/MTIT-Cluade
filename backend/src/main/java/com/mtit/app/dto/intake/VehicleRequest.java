package com.mtit.app.dto.intake;

import com.mtit.app.model.enums.FuelType;
import com.mtit.app.model.enums.TransmissionType;
import com.mtit.app.model.enums.VehicleType;
import com.mtit.app.model.enums.WarrantyStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequest {

    @NotBlank(message = "Vehicle name is required")
    @Size(max = 100)
    private String vehicleName;

    @NotBlank(message = "Registration number is required")
    @Size(max = 20)
    private String registrationNumber;

    @NotBlank(message = "Chassis number is required")
    @Size(max = 30)
    private String chassisNumber;

    @Size(max = 30)
    private String engineNumber;

    @NotBlank(message = "Brand is required")
    @Size(max = 50)
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(max = 50)
    private String model;

    @NotNull(message = "Manufacturing year is required")
    @Min(value = 1900, message = "Manufacturing year must be after 1900")
    private Integer manufacturingYear;

    @NotNull(message = "Vehicle type is required")
    private VehicleType vehicleType;

    @NotNull(message = "Fuel type is required")
    private FuelType fuelType;

    @NotNull(message = "Transmission type is required")
    private TransmissionType transmissionType;

    @NotNull(message = "Mileage is required")
    @Positive(message = "Mileage must be positive")
    private Double mileage;

    @Size(max = 30)
    private String color;

    @Size(max = 100)
    private String insuranceProvider;

    @FutureOrPresent(message = "Insurance expiry date cannot be in the past")
    private LocalDate insuranceExpiryDate;

    private WarrantyStatus warrantyStatus;

    private LocalDate lastServiceDate;
}
