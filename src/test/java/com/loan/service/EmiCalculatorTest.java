package com.loan.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EmiCalculatorTest {

    private final EmiCalculator calculator =
            new EmiCalculator();

    @Test
    void shouldCalculateCorrectEmi() {

        BigDecimal result = calculator.calculate(
                new BigDecimal("500000"),
                new BigDecimal("12"),
                36
        );

        assertEquals(
                new BigDecimal("16607.15"),
                result
        );
    }

    @Test
    void shouldCalculateZeroInterest() {

        BigDecimal result = calculator.calculate(
                new BigDecimal("120000"),
                BigDecimal.ZERO,
                12
        );

        assertEquals(
                new BigDecimal("10000.00"),
                result
        );
    }

    @Test
    void shouldRejectInvalidPrincipal() {

        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculate(
                        BigDecimal.ZERO,
                        new BigDecimal("12"),
                        36
                )
        );
    }
}