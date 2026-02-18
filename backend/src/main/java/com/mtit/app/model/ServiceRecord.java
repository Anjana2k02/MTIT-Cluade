package com.mtit.app.model;

import com.mtit.app.model.enums.ApprovalStatus;
import com.mtit.app.model.enums.PaymentMethod;
import com.mtit.app.model.enums.ServicePriority;
import com.mtit.app.model.enums.ServiceType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service_records", indexes = {
        @Index(name = "idx_sr_work_order", columnList = "workOrderNumber"),
        @Index(name = "idx_sr_vehicle", columnList = "vehicle_id"),
        @Index(name = "idx_sr_intake_time", columnList = "intakeTimestamp")
})
@SQLRestriction("deleted = false")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ServiceRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ServiceType serviceType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ServicePriority servicePriority;

    @Column(nullable = false, length = 2000)
    private String problemDescription;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal estimatedBudget;

    @Column(nullable = false)
    private LocalDate expectedCompletionDate;

    @Column(length = 100)
    private String assignedTechnician;

    @Column(precision = 12, scale = 2)
    private BigDecimal laborCost;

    @Column(precision = 12, scale = 2)
    private BigDecimal partsCost;

    @Column(precision = 12, scale = 2)
    private BigDecimal totalCost;

    private boolean customerApprovalRequired;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ApprovalStatus approvalStatus;

    private boolean serviceAgreementConfirmed;

    // Internal / Company Details
    @Column(length = 100)
    private String branchLocation;

    @Column(length = 100)
    private String serviceAdvisor;

    @Column(length = 100)
    private String custodian;

    @Column(nullable = false, unique = true, length = 20)
    private String workOrderNumber;

    @Column(nullable = false)
    private LocalDateTime intakeTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentMethod paymentMethod;

    @Column(precision = 5, scale = 2)
    private BigDecimal vatPercentage;

    @Column(precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(precision = 12, scale = 2)
    private BigDecimal finalPayableAmount;

    @Column(length = 2000)
    private String internalNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @ToString.Exclude
    private Vehicle vehicle;

    @OneToMany(mappedBy = "serviceRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<ServicePart> partsRequired = new ArrayList<>();
}
