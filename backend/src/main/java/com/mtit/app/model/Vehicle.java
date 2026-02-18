package com.mtit.app.model;

import com.mtit.app.model.enums.FuelType;
import com.mtit.app.model.enums.TransmissionType;
import com.mtit.app.model.enums.VehicleType;
import com.mtit.app.model.enums.WarrantyStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles", indexes = {
        @Index(name = "idx_vehicle_reg", columnList = "registrationNumber"),
        @Index(name = "idx_vehicle_chassis", columnList = "chassisNumber"),
        @Index(name = "idx_vehicle_customer", columnList = "customer_id")
})
@SQLRestriction("deleted = false")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Vehicle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String vehicleName;

    @Column(nullable = false, unique = true, length = 20)
    private String registrationNumber;

    @Column(nullable = false, unique = true, length = 30)
    private String chassisNumber;

    @Column(length = 30)
    private String engineNumber;

    @Column(nullable = false, length = 50)
    private String brand;

    @Column(nullable = false, length = 50)
    private String model;

    @Column(nullable = false)
    private Integer manufacturingYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransmissionType transmissionType;

    @Column(nullable = false)
    private Double mileage;

    @Column(length = 30)
    private String color;

    @Column(length = 100)
    private String insuranceProvider;

    private LocalDate insuranceExpiryDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private WarrantyStatus warrantyStatus;

    private LocalDate lastServiceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude
    private Customer customer;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<ServiceRecord> serviceRecords = new ArrayList<>();
}
