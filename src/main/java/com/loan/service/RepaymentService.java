package com.loan.service;

import com.loan.dto.RepaymentRequest;
import com.loan.entity.LoanAccount;
import com.loan.entity.Repayment;
import com.loan.repository.LoanAccountRepository;
import com.loan.repository.RepaymentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RepaymentService {

    private final LoanAccountRepository accountRepository;
    private final RepaymentRepository repaymentRepository;

    public RepaymentService(
            LoanAccountRepository accountRepository,
            RepaymentRepository repaymentRepository) {

        this.accountRepository = accountRepository;
        this.repaymentRepository = repaymentRepository;
    }

    @Transactional
    public Repayment pay(RepaymentRequest request) {

        if (repaymentRepository.existsByPaymentReference(
                request.paymentReference())) {
            throw new IllegalStateException(
                    "Duplicate payment reference");
        }

        LoanAccount account = accountRepository
                .findById(request.loanAccountId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Loan account not found"));

        if (!"ACTIVE".equals(account.getStatus())) {
            throw new IllegalStateException(
                    "Loan account is not active");
        }

        var amount = request.amount().setScale(
                2, RoundingMode.HALF_UP);

        if (amount.compareTo(
                account.getOutstandingBalance()) > 0) {
            throw new IllegalArgumentException(
                    "Payment exceeds outstanding balance");
        }

        account.setOutstandingBalance(
                account.getOutstandingBalance().subtract(amount));

        accountRepository.save(account);

        Repayment repayment = new Repayment();
        repayment.setLoanAccountId(account.getId());
        repayment.setPaymentReference(
                request.paymentReference());
        repayment.setAmount(amount);
        repayment.setPaymentDate(LocalDateTime.now());
        repayment.setStatus("SUCCESS");

        return repaymentRepository.save(repayment);
    }

    @Transactional(readOnly = true)
    public List<Repayment> history(UUID accountId) {

        if (!accountRepository.existsById(accountId)) {
            throw new IllegalArgumentException(
                    "Loan account not found");
        }

        return repaymentRepository
                .findByLoanAccountIdOrderByPaymentDateDesc(
                        accountId);
    }
}