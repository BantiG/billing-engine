
package com.example.billing.controller;

import com.example.billing.entity.Policy;
import com.example.billing.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
public class BillingController {
    private final BillingService service;

    @GetMapping("/{id}/schedule")
    public List<LocalDate> getSchedule(@PathVariable String id) {
        return service.getPremiumSchedule(id);
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity<Void> recordPayment(@PathVariable String id,
                                              @RequestParam boolean success) {
        service.recordPayment(id, success);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/delinquent")
    public List<Policy> getDelinquents() {
        return service.getDelinquentPolicies();
    }

    @PostMapping("/{id}/retry")
    public ResponseEntity<Void> retry(@PathVariable String id) {
        service.retryFailedPayments(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createPolicy(@Valid @RequestBody PolicyDTO dto) {
    service.createPolicy(dto);
    return ResponseEntity.ok().build();
}
}
