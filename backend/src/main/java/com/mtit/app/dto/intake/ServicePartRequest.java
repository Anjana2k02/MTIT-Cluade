package com.mtit.app.dto.intake;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicePartRequest {

    @NotBlank(message = "Part name is required")
    @Size(max = 100)
    private String partName;

    @Size(max = 50)
    private String partNumber;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @PositiveOrZero(message = "Unit price cannot be negative")
    private BigDecimal unitPrice;
}
