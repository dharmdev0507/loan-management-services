package com.loan.service;

import com.loan.dto.CustomerRequest;
import com.loan.entity.Customer;
import com.loan.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Customer create(CustomerRequest request) {

        if (repository.existsByEmail(request.email())) {
            throw new IllegalArgumentException(
                    "Customer email already registered");
        }

        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());
        customer.setKycVerified(request.kycVerified());

        return repository.save(customer);
    }
}