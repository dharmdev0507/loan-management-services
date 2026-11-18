package com.loan.controller;

import com.loan.dto.RepaymentRequest;
import com.loan.service.RepaymentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/repayments")
public class RepaymentController {

    private final RepaymentService service;

    public RepaymentController(RepaymentService service) {
        this.service = service;
    }

    @PostMapping
    public Object pay(
            @Valid @RequestBody RepaymentRequest request) {
        return service.pay(request);
    }

    @GetMapping("/{accountId}")
    public Object history(@PathVariable UUID accountId) {
        return service.history(accountId);
    }
}