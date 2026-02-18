package com.mtit.app.dto.intake;

import com.mtit.app.model.enums.ApprovalStatus;
import com.mtit.app.model.enums.ServicePriority;
import com.mtit.app.model.enums.ServiceType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDetailsResponse {

    private ServiceType serviceType;
    private ServicePriority servicePriority;
    private String problemDescription;
    private BigDecimal estimatedBudget;
    private LocalDate expectedCompletionDate;
    private String assignedTechnician;
    private List<ServicePartResponse> partsRequired;
    private BigDecimal laborCost;
    private BigDecimal partsCost;
    private BigDecimal totalCost;
    private boolean customerApprovalRequired;
    private ApprovalStatus approvalStatus;
    private boolean serviceAgreementConfirmed;
}
