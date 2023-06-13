package com.locancalculator.demo.repository;

import com.locancalculator.demo.model.InstallmentPlanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanCalculatorRepository extends JpaRepository<InstallmentPlanRequest, String> {
}
