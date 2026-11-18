package com.loan.workflow;

import com.loan.service.LoanService;
import org.camunda.bpm.engine.delegate.*;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component("rejectionDelegate")
public class RejectionDelegate implements JavaDelegate {

    private final LoanService loanService;

    public RejectionDelegate(LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    public void execute(DelegateExecution execution) {
        UUID id = UUID.fromString(
                (String) execution.getVariable("applicationId"));

        loanService.markRejected(id);
    }
}