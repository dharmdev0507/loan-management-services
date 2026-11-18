package com.loan.repository;

import com.loan.entity.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface RepaymentRepository
        extends JpaRepository<Repayment, UUID> {

    List<Repayment> findByLoanAccountIdOrderByPaymentDateDesc(
            UUID loanAccountId);

    boolean existsByPaymentReference(String reference);
}