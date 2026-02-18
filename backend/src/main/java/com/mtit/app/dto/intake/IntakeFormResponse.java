package com.mtit.app.dto.intake;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntakeFormResponse {

    private Long serviceRecordId;
    private String workOrderNumber;
    private LocalDateTime intakeTimestamp;
    private CustomerResponse customer;
    private VehicleResponse vehicle;
    private ServiceDetailsResponse service;
    private InternalDetailsResponse internal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
}
