package com.loan.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "lm_loan_account")
public class LoanAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false, unique = true)
    private UUID applicationId;

    @Column(nullable = false)
    private UUID customerId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal principal;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal outstandingBalance;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal emi;

    @Column(nullable = false, precision = 8, scale = 4)
    private BigDecimal interestRate;

    private Integer tenureMonths;

    private LocalDate disbursementDate;

    private String status;

    @Version
    private Long version;

    public UUID getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public UUID getApplicationId() { return applicationId; }
    public UUID getCustomerId() { return customerId; }
    public BigDecimal getPrincipal() { return principal; }
    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }
    public BigDecimal getEmi() { return emi; }
    public BigDecimal getInterestRate() { return interestRate; }
    public Integer getTenureMonths() { return tenureMonths; }
    public LocalDate getDisbursementDate() {
        return disbursementDate;
    }
    public String getStatus() { return status; }

    public void setAccountNumber(String value) {
        accountNumber = value;
    }
    public void setApplicationId(UUID value) {
        applicationId = value;
    }
    public void setCustomerId(UUID value) {
        customerId = value;
    }
    public void setPrincipal(BigDecimal value) {
        principal = value;
    }
    public void setOutstandingBalance(BigDecimal value) {
        outstandingBalance = value;
    }
    public void setEmi(BigDecimal value) { emi = value; }
    public void setInterestRate(BigDecimal value) {
        interestRate = value;
    }
    public void setTenureMonths(Integer value) {
        tenureMonths = value;
    }
    public void setDisbursementDate(LocalDate value) {
        disbursementDate = value;
    }
    public void setStatus(String value) { status = value; }
}
