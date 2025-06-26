package com.example.billing.controller;

import com.example.billing.entity.Policy;
import com.example.billing.repository.PolicyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BillingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PolicyRepository policyRepository;

    @BeforeEach
    void setup() {
        policyRepository.deleteAll();
        Policy policy = new Policy();
        policy.setId("MOCK123");
        policy.setStartDate(LocalDate.of(2024, 1, 1));
        policy.setEndDate(LocalDate.of(2024, 4, 1));
        policy.setPremiumAmount(new BigDecimal("120.00"));
        policyRepository.save(policy);
    }

    @Test
    void testGetPremiumSchedule() throws Exception {
        mockMvc.perform(get("/api/policies/MOCK123/schedule"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void testRecordFailedPayment() throws Exception {
        mockMvc.perform(post("/api/policies/MOCK123/payment")
                        .param("success", "false"))
                .andExpect(status().isOk());
    }

    @Test
    void testRecordSuccessfulPayment() throws Exception {
        mockMvc.perform(post("/api/policies/MOCK123/payment")
                        .param("success", "true"))
                .andExpect(status().isOk());
    }

    @Test
    void testRetryEndpoint() throws Exception {
        mockMvc.perform(post("/api/policies/MOCK123/retry"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetDelinquentPolicies() throws Exception {
        mockMvc.perform(post("/api/policies/MOCK123/payment")
                        .param("success", "false"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/policies/delinquent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testInvalidPolicySchedule() throws Exception {
        mockMvc.perform(get("/api/policies/INVALID123/schedule"))
                .andExpect(status().is4xxClientError());
    }
}
