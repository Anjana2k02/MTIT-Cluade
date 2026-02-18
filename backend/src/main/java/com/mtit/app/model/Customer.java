package com.mtit.app.model;

import com.mtit.app.model.enums.CustomerType;
import com.mtit.app.model.enums.PreferredContactMethod;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers", indexes = {
        @Index(name = "idx_customer_nic", columnList = "nicNumber"),
        @Index(name = "idx_customer_email", columnList = "email"),
        @Index(name = "idx_customer_type", columnList = "customerType")
})
@SQLRestriction("deleted = false")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 20)
    private String nicNumber;

    @Column(nullable = false, length = 15)
    private String primaryContact;

    @Column(length = 15)
    private String secondaryContact;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 200)
    private String street;

    @Column(length = 100)
    private String city;

    @Column(length = 10)
    private String postalCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CustomerType customerType;

    @Column(length = 50)
    private String companyRegistrationNumber;

    @Column(length = 50)
    private String taxId;

    private LocalDate customerSince;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private PreferredContactMethod preferredContactMethod;

    @Column(length = 100)
    private String emergencyContactName;

    @Column(length = 15)
    private String emergencyContactPhone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Vehicle> vehicles = new ArrayList<>();
}
