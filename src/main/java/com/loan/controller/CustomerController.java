package com.loan.controller;

import com.loan.dto.CustomerRequest;
import com.loan.service.CustomerService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public Object create(
            @Valid @RequestBody CustomerRequest request) {
        return service.create(request);
    }
}