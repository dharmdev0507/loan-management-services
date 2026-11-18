package com.loan.repository;

import com.loan.entity.LoanAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;

public interface LoanAccountRepository
        extends JpaRepository<LoanAccount, UUID> {

    Optional<LoanAccount> findByApplicationId(UUID applicationId);

    Optional<LoanAccount> findByAccountNumber(String accountNumber);
}