package com.mtit.app.dto.intake;

import com.mtit.app.model.enums.ApprovalStatus;
import com.mtit.app.model.enums.ServicePriority;
import com.mtit.app.model.enums.ServiceType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDetailsRequest {

    @NotNull(message = "Service type is required")
    private ServiceType serviceType;

    @NotNull(message = "Service priority is required")
    private ServicePriority servicePriority;

    @NotBlank(message = "Problem description is required")
    @Size(min = 50, message = "Problem description must be at least 50 characters")
    @Size(max = 2000)
    private String problemDescription;

    @NotNull(message = "Estimated budget is required")
    @Positive(message = "Estimated budget must be positive")
    private BigDecimal estimatedBudget;

    @NotNull(message = "Expected completion date is required")
    @FutureOrPresent(message = "Expected completion date cannot be before today")
    private LocalDate expectedCompletionDate;

    @Size(max = 100)
    private String assignedTechnician;

    @Valid
    private List<ServicePartRequest> partsRequired;

    @PositiveOrZero(message = "Labor cost cannot be negative")
    private BigDecimal laborCost;

    @PositiveOrZero(message = "Parts cost cannot be negative")
    private BigDecimal partsCost;

    private boolean customerApprovalRequired;

    private ApprovalStatus approvalStatus;

    private boolean serviceAgreementConfirmed;
}
