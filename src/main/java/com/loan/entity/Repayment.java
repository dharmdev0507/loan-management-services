package com.loan.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "lm_repayment",
        uniqueConstraints = @UniqueConstraint(
                columnNames = "payment_reference"))
public class Repayment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID loanAccountId;

    @Column(nullable = false, unique = true)
    private String paymentReference;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    private String status;

    public UUID getId() { return id; }
    public UUID getLoanAccountId() { return loanAccountId; }
    public String getPaymentReference() {
        return paymentReference;
    }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public String getStatus() { return status; }

    public void setLoanAccountId(UUID value) {
        loanAccountId = value;
    }
    public void setPaymentReference(String value) {
        paymentReference = value;
    }
    public void setAmount(BigDecimal value) {
        amount = value;
    }
    public void setPaymentDate(LocalDateTime value) {
        paymentDate = value;
    }
    public void setStatus(String value) { status = value; }
}