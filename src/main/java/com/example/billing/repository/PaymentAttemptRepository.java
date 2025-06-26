
package com.example.billing.repository;

import com.example.billing.entity.PaymentAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentAttemptRepository extends JpaRepository<PaymentAttempt, Long> {
    List<PaymentAttempt> findByPolicyIdAndSuccessFalse(String policyId);
}
