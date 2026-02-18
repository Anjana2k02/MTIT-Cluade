package com.mtit.app.dto.intake;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntakeFormRequest {

    @Valid
    @NotNull(message = "Customer details are required")
    private CustomerRequest customer;

    @Valid
    @NotNull(message = "Vehicle details are required")
    private VehicleRequest vehicle;

    @Valid
    @NotNull(message = "Service details are required")
    private ServiceDetailsRequest service;

    @Valid
    private InternalDetailsRequest internal;
}
