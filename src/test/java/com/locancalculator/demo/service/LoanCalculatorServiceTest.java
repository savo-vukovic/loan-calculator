package com.locancalculator.demo.service;

import com.locancalculator.demo.dto.Payment;
import com.locancalculator.demo.repository.LoanCalculatorRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoanCalculatorServiceTest {

    @InjectMocks
    private LoanCalculatorService loanCalculatorService;
    @Mock
    private LoanCalculatorRepository loanCalculatorRepository;

    private static Stream<Arguments> inputParameters() {
        return Stream.of(
                Arguments.of(20000.0, 5.0, 5),
                Arguments.of(20000.0, 5.0, 60),
                Arguments.of(1000.0, 4.0, 12),
                Arguments.of(4567.0, 5.6, 10)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "inputParameters")
    public void testCalculatingInstallmentPlan(Double amount, Double interestRate, Integer numberOfPayments) {
        // given

        // when
        var installmentPlan = loanCalculatorService.calculateInstallmentPlan(amount, numberOfPayments, interestRate);

        // then
        assertEquals(numberOfPayments, installmentPlan.size());

        double totalPayedAmount = 0;
        double totalInterestAmount = 0;
        for (Payment payment : installmentPlan) {
            assertEquals(payment.getPaymentAmount(), payment.getPrincipalAmount() + payment.getInterestAmount());

            totalPayedAmount += payment.getPaymentAmount();
            totalInterestAmount += payment.getInterestAmount();

        }
        assertEquals(amount, totalPayedAmount - totalInterestAmount, 0.0000001d);
        assertEquals(0.0, installmentPlan.get(installmentPlan.size() - 1).getBalanceOwned());

        verify(loanCalculatorRepository).save(any());
    }
}