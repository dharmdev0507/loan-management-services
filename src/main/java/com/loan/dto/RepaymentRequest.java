package com.loan.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record RepaymentRequest(
        @NotNull UUID loanAccountId,
        @NotBlank String paymentReference,
        @NotNull @DecimalMin("0.01") BigDecimal amount
) {}