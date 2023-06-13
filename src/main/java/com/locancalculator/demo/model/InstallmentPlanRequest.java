package com.locancalculator.demo.model;


import com.locancalculator.demo.dto.Payment;
import com.locancalculator.demo.utils.PaymentUtils;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Table
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentPlanRequest {


    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private Double amount;
    private Double interestPercentage;
    private Integer numberOfMonths;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InstallmentPlanPayment> paymentList;

    public InstallmentPlanRequest(Double amount, Double interest, Integer numberOfMonths, List<Payment> payments) {
        this.amount = amount;
        this.interestPercentage = interest;
        this.numberOfMonths = numberOfMonths;
        this.paymentList = payments.stream().map(PaymentUtils::from).toList();
        this.paymentList.forEach(payment -> payment.setRequest(this));
    }
}
