package com.mtit.app.dto.intake;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicePartResponse {

    private Long id;
    private String partName;
    private String partNumber;
    private Integer quantity;
    private BigDecimal unitPrice;
}
