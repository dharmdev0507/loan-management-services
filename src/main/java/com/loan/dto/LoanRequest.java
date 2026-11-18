package com.loan.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record LoanRequest(
        @NotNull UUID customerId,
        @NotBlank String loanType,
        @NotNull
        @DecimalMin("1000.00")
        BigDecimal requestedAmount,
        @NotNull
        @Min(1)
        @Max(360)
        Integer tenureMonths,
        @NotNull
        @DecimalMin("0.00")
        @DecimalMax("36.00")
        BigDecimal annualInterestRate
) {}