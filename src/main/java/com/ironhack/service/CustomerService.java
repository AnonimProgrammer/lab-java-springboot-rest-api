package com.ironhack.service;

import com.ironhack.dto.request.CustomerRequest;
import com.ironhack.dto.response.CustomerResponse;
import com.ironhack.exception.NotFoundException;
import com.ironhack.mapper.CustomerMapper;
import com.ironhack.model.Customer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomerService {
    private final Map<UUID, Customer> customers = new HashMap<>();

    private final CustomerMapper customerMapper;

    public CustomerService(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public CustomerResponse create(CustomerRequest request) {
        Customer customer = customerMapper.toModel(request);
        checkForDuplication(customer);
        customers.put(customer.getId(), customer);
        return customerMapper.toResponse(customer);
    }

    public CustomerResponse getByEmail(String email) {
        return customers.values().stream()
                .filter(customer -> customer.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .map(customerMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Customer with email " + email + " not found"));
    }

    public List<CustomerResponse> getAll() {
        return customers.values().stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    private void checkForDuplication(Customer customer) {
        boolean emailExists = customers.values().stream()
                .anyMatch(existingCustomer -> existingCustomer.getEmail().equalsIgnoreCase(customer.getEmail()));

        if (emailExists) {
            throw new IllegalArgumentException("Customer with email " + customer.getEmail() + " already exists");
        }
    }
}
