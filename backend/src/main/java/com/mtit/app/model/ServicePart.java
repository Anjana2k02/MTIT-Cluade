package com.mtit.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "service_parts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String partName;

    @Column(length = 50)
    private String partNumber;

    @Column(nullable = false)
    private Integer quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_record_id", nullable = false)
    @ToString.Exclude
    private ServiceRecord serviceRecord;
}
