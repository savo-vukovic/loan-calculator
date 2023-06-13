package com.locancalculator.demo.service;

import com.locancalculator.demo.dto.Payment;
import com.locancalculator.demo.model.InstallmentPlanRequest;
import com.locancalculator.demo.repository.LoanCalculatorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class LoanCalculatorService {

    private final LoanCalculatorRepository repository;

    public List<Payment> calculateInstallmentPlan(Double amount, Integer numberOfMonths, Double interestPercentage) {

        Double interestRate = interestPercentage / 100 / 12;
        Double fixedPaymentAmount = calculatePayment(amount, numberOfMonths, interestRate);
        List<Payment> installmentPlan = new ArrayList<>();
        for (int i = 0; i < numberOfMonths; i++) {
            var payment = new Payment();
            double interest = interestRate * amount;
            payment.setInterestAmount(interest);

            if (isLastPayment(i, numberOfMonths)) {
                payment.setPaymentAmount(amount + interest);
                payment.setBalanceOwned(amount - (payment.getPaymentAmount() - interest));
            } else {
                payment.setPaymentAmount(fixedPaymentAmount);
                payment.setBalanceOwned(amount - payment.getPaymentAmount() + interest);
            }

            payment.setPrincipalAmount(payment.getPaymentAmount() - interest);
            installmentPlan.add(payment);
            amount = payment.getBalanceOwned();
        }
        var request = new InstallmentPlanRequest(amount, interestRate, numberOfMonths, installmentPlan);
        repository.save(request);
        return installmentPlan;
    }

    private boolean isLastPayment(int iterator, int numberOfMonths) {
        return iterator + 1 == numberOfMonths;
    }

    private Double calculatePayment(Double amount, Integer numberOfMonths, Double interestRate) {
        Double N = Math.pow(1.0 + interestRate, numberOfMonths);
        return (amount * interestRate * N) / (N - 1.0);
    }
}
