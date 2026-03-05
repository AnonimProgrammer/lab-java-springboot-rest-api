package com.ironhack.controller;

import com.ironhack.dto.request.CustomerRequest;
import com.ironhack.dto.response.CustomerResponse;
import com.ironhack.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping(params = "email")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(
            @RequestParam String email
    ) {
        return ResponseEntity.ok(customerService.getByEmail(email));
    }
}
