package com.loan.controller;

import com.loan.dto.*;
import com.loan.service.LoanService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    @PostMapping
    public Object submit(
            @Valid @RequestBody LoanRequest request) {
        return service.submit(request);
    }

    @GetMapping("/{id}")
    public Object get(@PathVariable UUID id) {
        return service.getApplication(id);
    }

    @GetMapping("/approvals/tasks")
    public Object tasks() {
        return service.pendingTasks().stream()
                .map(task -> Map.of(
                        "taskId", task.getId(),
                        "taskName", task.getName(),
                        "created", task.getCreateTime()
                ))
                .toList();
    }

    @PostMapping("/approvals/{taskId}/decision")
    public Map<String, String> decide(
            @PathVariable("taskId") String taskId,
            @Valid @RequestBody ApprovalRequest request) {

        service.decide(
                taskId,
                request.decision(),
                request.remarks());

        return Map.of("message",
                "Approval decision submitted successfully");
    }

    //@PostMapping("/{id}/close")
    @PostMapping("/accounts/{id}/close")
    public Map<String, String> close(
            @PathVariable("id") UUID id) {

        service.closeLoan(id);

        return Map.of("message", "Loan closed successfully");
    }
}