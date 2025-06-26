
package com.example.billing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Policy {
    @Id
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal premiumAmount;
    private boolean delinquent = false;
}
