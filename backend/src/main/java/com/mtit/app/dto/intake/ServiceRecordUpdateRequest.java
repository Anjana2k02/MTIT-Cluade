package com.mtit.app.dto.intake;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRecordUpdateRequest {

    @Valid
    @NotNull(message = "Service details are required")
    private ServiceDetailsRequest service;

    @Valid
    private InternalDetailsRequest internal;
}
