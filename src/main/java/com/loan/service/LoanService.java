package com.loan.service;

import com.loan.dto.LoanRequest;
import com.loan.entity.*;
import com.loan.repository.*;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class LoanService {

    private final LoanApplicationRepository applicationRepository;
    private final CustomerRepository customerRepository;
    private final LoanAccountRepository accountRepository;
    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final EmiCalculator emiCalculator;

    public LoanService(
            LoanApplicationRepository applicationRepository,
            CustomerRepository customerRepository,
            LoanAccountRepository accountRepository,
            RuntimeService runtimeService,
            TaskService taskService,
            EmiCalculator emiCalculator) {

        this.applicationRepository = applicationRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.emiCalculator = emiCalculator;
    }

    @Transactional
    public LoanApplication submit(LoanRequest request) {

        Customer customer = customerRepository
                .findById(request.customerId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Customer not found"));

        if (!customer.isKycVerified()) {
            throw new IllegalStateException(
                    "Customer KYC verification is required");
        }

        LoanApplication application = new LoanApplication();

        application.setApplicationNumber(
                "LN-" + UUID.randomUUID()
                        .toString().substring(0, 12).toUpperCase());

        application.setCustomerId(customer.getId());
        application.setLoanType(request.loanType());
        application.setRequestedAmount(request.requestedAmount());
        application.setTenureMonths(request.tenureMonths());
        application.setAnnualInterestRate(
                request.annualInterestRate());
        application.setStatus("SUBMITTED");

        application = applicationRepository.save(application);

        Map<String, Object> variables = new HashMap<>();
        variables.put("applicationId",
                application.getId().toString());

        var process = runtimeService.startProcessInstanceByKey(
                "loanLifecycle",
                application.getId().toString(),
                variables);

        application.setProcessInstanceId(process.getId());

        return applicationRepository.save(application);
    }

    @Transactional(readOnly = true)
    public LoanApplication getApplication(UUID id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Loan application not found"));
    }

    @Transactional(readOnly = true)
    public List<Task> pendingTasks() {
        return taskService.createTaskQuery()
                .processDefinitionKey("loanLifecycle")
                .active()
                .orderByTaskCreateTime()
                .desc()
                .list();
    }

    @Transactional
    public void decide(
            String taskId,
            String decision,
            String remarks) {

        if (!"APPROVED".equalsIgnoreCase(decision)
                && !"REJECTED".equalsIgnoreCase(decision)) {
            throw new IllegalArgumentException(
                    "Decision must be APPROVED or REJECTED");
        }

        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();

        if (task == null) {
            throw new NoSuchElementException(
                    "Approval task not found");
        }

        if (!"Credit Manager Approval".equals(task.getName())) {
            throw new IllegalStateException(
                    "This task is not a credit approval task");
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put("approved",
                "APPROVED".equalsIgnoreCase(decision));
        variables.put("approvalRemarks", remarks);

        taskService.complete(taskId, variables);
    }

    @Transactional
    public LoanAccount disburse(UUID applicationId) {

        LoanApplication application = getApplication(applicationId);

        if (!"APPROVED".equals(application.getStatus())) {
            throw new IllegalStateException(
                    "Only approved applications can be disbursed");
        }

        if (accountRepository.findByApplicationId(applicationId)
                .isPresent()) {
            throw new IllegalStateException(
                    "Loan already disbursed");
        }

        BigDecimal principal = application.getRequestedAmount();

        BigDecimal emi = emiCalculator.calculate(
                principal,
                application.getAnnualInterestRate(),
                application.getTenureMonths());

        LoanAccount account = new LoanAccount();

        account.setAccountNumber(
                "LA-" + UUID.randomUUID()
                        .toString().substring(0, 12).toUpperCase());

        account.setApplicationId(applicationId);
        account.setCustomerId(application.getCustomerId());
        account.setPrincipal(principal);
        account.setOutstandingBalance(principal);
        account.setEmi(emi);
        account.setInterestRate(
                application.getAnnualInterestRate());
        account.setTenureMonths(application.getTenureMonths());
        account.setDisbursementDate(LocalDate.now());
        account.setStatus("ACTIVE");

        application.setStatus("DISBURSED");
        applicationRepository.save(application);

        return accountRepository.save(account);
    }

    @Transactional
    public void closeLoan(UUID accountId) {

        LoanAccount account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Loan account not found"));

        if (account.getOutstandingBalance().signum() != 0) {
            throw new IllegalStateException(
                    "Loan has outstanding balance");
        }

        if (!"ACTIVE".equals(account.getStatus())) {
            throw new IllegalStateException(
                    "Loan account is not active");
        }

        account.setStatus("CLOSED");
        accountRepository.save(account);
    }

    @Transactional
    public void markApproved(UUID applicationId) {
        LoanApplication application = getApplication(applicationId);

        if (!"UNDER_REVIEW".equals(application.getStatus())) {
            throw new IllegalStateException(
                    "Application is not awaiting approval");
        }

        application.setStatus("APPROVED");
        applicationRepository.save(application);
    }

    @Transactional
    public void markRejected(UUID applicationId) {
        LoanApplication application = getApplication(applicationId);

        application.setStatus("REJECTED");
        applicationRepository.save(application);
    }
}