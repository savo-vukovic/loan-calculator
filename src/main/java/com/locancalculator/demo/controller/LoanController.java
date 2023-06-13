package com.locancalculator.demo.controller;

import com.locancalculator.demo.dto.Payment;
import com.locancalculator.demo.service.LoanCalculatorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class LoanController {

    private LoanCalculatorService loanCalculatorService;

    @GetMapping("/installment/plan")
    public List<Payment> getInstallmentPlan(@RequestParam Double amount,
                                            @RequestParam Double interestPercentage,
                                            @RequestParam Integer numberOfMonths) {
        return loanCalculatorService.calculateInstallmentPlan(amount, numberOfMonths, interestPercentage);
    }
}
