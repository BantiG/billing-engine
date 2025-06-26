
package com.example.billing.service;

import com.example.billing.entity.PaymentAttempt;
import com.example.billing.entity.Policy;
import com.example.billing.repository.PaymentAttemptRepository;
import com.example.billing.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingService {
    private final PolicyRepository policyRepo;
    private final PaymentAttemptRepository paymentRepo;

    public List<LocalDate> getPremiumSchedule(String policyId) {
        Policy policy = policyRepo.findById(policyId).orElseThrow();
        List<LocalDate> schedule = new ArrayList<>();
        LocalDate date = policy.getStartDate();
        while (date.isBefore(policy.getEndDate())) {
            schedule.add(date);
            date = date.plusMonths(1);
        }
        return schedule;
    }

    public void recordPayment(String policyId, boolean success) {
        PaymentAttempt attempt = new PaymentAttempt();
        attempt.setPolicyId(policyId);
        attempt.setAttemptTime(LocalDateTime.now());
        attempt.setSuccess(success);
        attempt.setRetryCount(0);
        paymentRepo.save(attempt);

        if (!success) {
            policyRepo.findById(policyId).ifPresent(policy -> {
                policy.setDelinquent(true);
                policyRepo.save(policy);
            });
        }
    }

    public List<Policy> getDelinquentPolicies() {
        return policyRepo.findAll().stream()
                .filter(Policy::isDelinquent)
                .collect(Collectors.toList());
    }

    public void retryFailedPayments(String policyId) {
        List<PaymentAttempt> attempts = paymentRepo.findByPolicyIdAndSuccessFalse(policyId);
        for (PaymentAttempt attempt : attempts) {
            if (attempt.getRetryCount() < 3) {
                attempt.setRetryCount(attempt.getRetryCount() + 1);
                attempt.setAttemptTime(LocalDateTime.now());
                paymentRepo.save(attempt);
            }
        }
    }

    public void createPolicy(PolicyDTO dto) {
        Policy policy = new Policy();
        policy.setId(dto.getId());
        policy.setStartDate(dto.getStartDate());
        policy.setEndDate(dto.getEndDate());
        policy.setPremiumAmount(dto.getPremiumAmount());
        policyRepo.save(policy);
}
}
