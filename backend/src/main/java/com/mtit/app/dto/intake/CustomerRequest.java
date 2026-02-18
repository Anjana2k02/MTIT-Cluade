package com.mtit.app.dto.intake;

import com.mtit.app.model.enums.CustomerType;
import com.mtit.app.model.enums.PreferredContactMethod;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 100)
    private String fullName;

    @NotBlank(message = "NIC number is required")
    @Pattern(regexp = "^(\\d{9}[VvXx]|\\d{12})$", message = "NIC must be 9 digits followed by V/X or 12 digits")
    private String nicNumber;

    @NotBlank(message = "Primary contact is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String primaryContact;

    @Pattern(regexp = "^(\\+?[0-9]{10,15})?$", message = "Invalid phone number format")
    private String secondaryContact;

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email address")
    private String email;

    @Size(max = 200)
    private String street;

    @Size(max = 100)
    private String city;

    @Size(max = 10)
    private String postalCode;

    @NotNull(message = "Customer type is required")
    private CustomerType customerType;

    @Size(max = 50)
    private String companyRegistrationNumber;

    @Size(max = 50)
    private String taxId;

    @PastOrPresent(message = "Customer since date cannot be in the future")
    private LocalDate customerSince;

    private PreferredContactMethod preferredContactMethod;

    @Size(max = 100)
    private String emergencyContactName;

    @Pattern(regexp = "^(\\+?[0-9]{10,15})?$", message = "Invalid phone number format")
    private String emergencyContactPhone;
}
