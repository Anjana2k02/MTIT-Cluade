package com.mtit.app.dto.intake;

import com.mtit.app.model.enums.PaymentMethod;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternalDetailsRequest {

    @Size(max = 100)
    private String branchLocation;

    @Size(max = 100)
    private String serviceAdvisor;

    @Size(max = 100)
    private String custodian;

    private PaymentMethod paymentMethod;

    @DecimalMin(value = "0.0", message = "VAT percentage cannot be negative")
    @DecimalMax(value = "100.0", message = "VAT percentage cannot exceed 100")
    private BigDecimal vatPercentage;

    @DecimalMin(value = "0.0", message = "Discount percentage cannot be negative")
    @DecimalMax(value = "100.0", message = "Discount percentage cannot exceed 100")
    private BigDecimal discountPercentage;

    @Size(max = 2000)
    private String internalNotes;
}
