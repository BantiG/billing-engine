
package com.example.billing.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PolicyDTO {
    @NotBlank(message = "Policy ID is required")
    private String id;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @DecimalMin(value = "0.0", inclusive = false, message = "Premium must be greater than zero")
    private BigDecimal premiumAmount;
}
