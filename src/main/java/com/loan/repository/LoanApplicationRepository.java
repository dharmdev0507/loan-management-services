package com.loan.repository;

import com.loan.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;

public interface LoanApplicationRepository
        extends JpaRepository<LoanApplication, UUID> {

    Optional<LoanApplication> findByApplicationNumber(
            String applicationNumber);

    boolean existsByApplicationNumber(String applicationNumber);
}