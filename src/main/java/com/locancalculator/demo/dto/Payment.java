package com.locancalculator.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {

    private Double paymentAmount;
    private Double principalAmount;
    private Double interestAmount;
    private Double balanceOwned;
}
