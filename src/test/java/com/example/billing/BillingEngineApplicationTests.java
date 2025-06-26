
package com.example.billing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BillingEngineApplicationTests {
    @Autowired
    private BillingService billingService;

    @Test
    void contextLoads() {
        assertNotNull(billingService, "BillingService should be initialized");
    }
}
