package com.loan.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "lm_loan_application")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String applicationNumber;

    @Column(nullable = false)
    private UUID customerId;

    @Column(nullable = false)
    private String loanType;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal requestedAmount;

    @Column(nullable = false)
    private Integer tenureMonths;

    @Column(nullable = false, precision = 8, scale = 4)
    private BigDecimal annualInterestRate;

    @Column(nullable = false)
    private String status;

    private String processInstanceId;

    private String rejectionReason;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Version
    private Long version;

    @PrePersist
    public void beforeInsert() {
        createdAt = LocalDateTime.now();
        if (status == null) status = "SUBMITTED";
    }

    public UUID getId() { return id; }
    public String getApplicationNumber() {
        return applicationNumber;
    }
    public UUID getCustomerId() { return customerId; }
    public String getLoanType() { return loanType; }
    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }
    public Integer getTenureMonths() { return tenureMonths; }
    public BigDecimal getAnnualInterestRate() {
        return annualInterestRate;
    }
    public String getStatus() { return status; }
    public String getProcessInstanceId() {
        return processInstanceId;
    }
    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setApplicationNumber(String value) {
        applicationNumber = value;
    }
    public void setCustomerId(UUID value) {
        customerId = value;
    }
    public void setLoanType(String value) { loanType = value; }
    public void setRequestedAmount(BigDecimal value) {
        requestedAmount = value;
    }
    public void setTenureMonths(Integer value) {
        tenureMonths = value;
    }
    public void setAnnualInterestRate(BigDecimal value) {
        annualInterestRate = value;
    }
    public void setStatus(String value) { status = value; }
    public void setProcessInstanceId(String value) {
        processInstanceId = value;
    }
    public void setRejectionReason(String value) {
        rejectionReason = value;
    }
}