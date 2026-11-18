package com.loan.workflow;

import com.loan.service.LoanService;
import org.camunda.bpm.engine.delegate.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("disbursementDelegate")
public class DisbursementDelegate implements JavaDelegate {

    private final LoanService loanService;

    public DisbursementDelegate(LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    public void execute(DelegateExecution execution) {

        UUID applicationId = UUID.fromString(
                (String) execution.getVariable("applicationId"));

        var account = loanService.disburse(applicationId);

        execution.setVariable(
                "loanAccountId", account.getId().toString());

        execution.setVariable(
                "accountNumber", account.getAccountNumber());
    }
}