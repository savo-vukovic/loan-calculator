package com.locancalculator.demo;

import com.locancalculator.demo.repository.LoanCalculatorRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoanCalculatorIT {

    private static final Double AMOUNT = 20000.0;
    private static final Double INTEREST_PERCENTAGE = 5.0;
    private static final Integer NUMBER_OF_MONTHS = 12;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LoanCalculatorRepository repository;

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    @Transactional
    public void testGettingInstallmentPlan() throws Exception {
        // given
        var params = new LinkedMultiValueMap<String, String>();
        params.add("amount", String.valueOf(AMOUNT));
        params.add("interestPercentage", String.valueOf(INTEREST_PERCENTAGE));
        params.add("numberOfMonths", String.valueOf(NUMBER_OF_MONTHS));

        // when/then
        mockMvc.perform(get("/installment/plan").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(NUMBER_OF_MONTHS)))
                .andExpect(jsonPath("$[-1].balanceOwned", equalTo(0.0)))
                .andReturn();

        // then
        var installmentPlanRequests = repository.findAll();
        assertFalse(installmentPlanRequests.isEmpty());
        assertEquals(1, installmentPlanRequests.size());

        var installmentPlanRequest = installmentPlanRequests.get(0);

        double totalPaymentAmount = 0;
        double totalInterestAmount = 0;

        for (var payment : installmentPlanRequest.getPaymentList()) {
            totalPaymentAmount += payment.getPaymentAmount();
            totalInterestAmount += payment.getInterestAmount();
        }
        assertEquals(AMOUNT, totalPaymentAmount - totalInterestAmount, 0.00001d);
    }
}
