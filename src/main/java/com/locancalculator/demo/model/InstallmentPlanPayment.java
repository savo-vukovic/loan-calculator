package com.locancalculator.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Table
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentPlanPayment {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private Double paymentAmount;
    private Double principalAmount;
    private Double interestAmount;
    private Double balanceOwned;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private InstallmentPlanRequest request;
}
