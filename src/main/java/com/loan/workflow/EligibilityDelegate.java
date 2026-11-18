package com.loan.workflow;

import com.loan.entity.Customer;
import com.loan.entity.LoanApplication;
import com.loan.repository.CustomerRepository;
import com.loan.repository.LoanApplicationRepository;

import org.camunda.bpm.engine.delegate.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component("eligibilityDelegate")
public class EligibilityDelegate implements JavaDelegate {

    private final LoanApplicationRepository loanRepository;
    private final CustomerRepository customerRepository;

    public EligibilityDelegate(
            LoanApplicationRepository loanRepository,
            CustomerRepository customerRepository) {

        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void execute(DelegateExecution execution) {

        UUID id = UUID.fromString(
                (String) execution.getVariable("applicationId"));

        LoanApplication loan = loanRepository.findById(id)
                .orElseThrow();

        Customer customer = customerRepository
                .findById(loan.getCustomerId())
                .orElseThrow();

        boolean eligible =
                customer.isKycVerified()
                        && loan.getRequestedAmount().compareTo(
                        new BigDecimal("1000000")) <= 0
                        && loan.getTenureMonths() <= 360;

        execution.setVariable("eligible", eligible);

        if (!eligible) {
            loan.setStatus("REJECTED");
            loanRepository.save(loan);
        } else {
            loan.setStatus("UNDER_REVIEW");
            loanRepository.save(loan);
        }
    }
}