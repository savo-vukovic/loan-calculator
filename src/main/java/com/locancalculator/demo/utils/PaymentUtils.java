package com.locancalculator.demo.utils;

import com.locancalculator.demo.dto.Payment;
import com.locancalculator.demo.model.InstallmentPlanPayment;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentUtils {

    public static InstallmentPlanPayment from(Payment payment) {
        var result = new InstallmentPlanPayment();
        result.setPaymentAmount(payment.getPaymentAmount());
        result.setBalanceOwned(payment.getBalanceOwned());
        result.setInterestAmount(payment.getInterestAmount());
        result.setPrincipalAmount(payment.getPrincipalAmount());
        return result;
    }
}
