package com.mtit.app.dto.intake;

import com.mtit.app.model.enums.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InternalDetailsResponse {

    private String branchLocation;
    private String serviceAdvisor;
    private String custodian;
    private String workOrderNumber;
    private LocalDateTime intakeTimestamp;
    private PaymentMethod paymentMethod;
    private BigDecimal vatPercentage;
    private BigDecimal discountPercentage;
    private BigDecimal finalPayableAmount;
    private String internalNotes;
}
