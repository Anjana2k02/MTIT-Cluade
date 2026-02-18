package com.mtit.app.dto.intake;

import com.mtit.app.model.enums.CustomerType;
import com.mtit.app.model.enums.PreferredContactMethod;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Long id;
    private String fullName;
    private String nicNumber;
    private String primaryContact;
    private String secondaryContact;
    private String email;
    private String street;
    private String city;
    private String postalCode;
    private CustomerType customerType;
    private String companyRegistrationNumber;
    private String taxId;
    private LocalDate customerSince;
    private PreferredContactMethod preferredContactMethod;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
}
