package com.loan.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class EmiCalculator {

    private static final MathContext MC =
            new MathContext(24, RoundingMode.HALF_UP);

    public BigDecimal calculate(
            BigDecimal principal,
            BigDecimal annualRate,
            int months) {

        if (principal == null || principal.signum() <= 0
                || annualRate == null
                || annualRate.signum() < 0
                || months <= 0) {
            throw new IllegalArgumentException(
                    "Invalid EMI calculation parameters");
        }

        BigDecimal monthlyRate =
                annualRate.divide(
                        new BigDecimal("1200"), MC);

        if (monthlyRate.signum() == 0) {
            return principal.divide(
                    BigDecimal.valueOf(months),
                    2, RoundingMode.HALF_UP);
        }

        BigDecimal factor = BigDecimal.ONE
                .add(monthlyRate, MC)
                .pow(months, MC);

        BigDecimal numerator = principal
                .multiply(monthlyRate, MC)
                .multiply(factor, MC);

        BigDecimal denominator =
                factor.subtract(BigDecimal.ONE, MC);

        return numerator.divide(
                denominator, 2, RoundingMode.HALF_UP);
    }
}