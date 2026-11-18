package com.loan.dto;

import jakarta.validation.constraints.NotBlank;

public record ApprovalRequest(
        @NotBlank String decision,
        String remarks
) {}