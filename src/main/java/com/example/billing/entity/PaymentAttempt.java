
package com.example.billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class PaymentAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String policyId;
    private LocalDateTime attemptTime;
    private boolean success;
    private int retryCount = 0;
}
