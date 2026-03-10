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
        checkForEmailDuplication(customer.getEmail());

        customers.put(customer.getId(), customer);
        return customerMapper.toResponse(customer);
    }

    public List<CustomerResponse> getCustomers(
            String email
    ) {
        return customers.values().stream()
                .filter(customer -> email == null || customer.getEmail().contains(email))
                .map(customerMapper::toResponse)
                .toList();
    }

    public CustomerResponse getById(UUID id) {
        Customer customer = findOrThrow(id);
        return customerMapper.toResponse(customer);
    }

    public CustomerResponse update(UUID id, CustomerRequest request) {
        Customer existingCustomer = findOrThrow(id);

        checkForEmailDuplication(request.email());

        existingCustomer.setName(request.name());
        existingCustomer.setEmail(request.email());
        existingCustomer.setAddress(request.address());
        existingCustomer.setAge(request.age());

        return customerMapper.toResponse(existingCustomer);
    }

    public void delete(UUID id) {
        findOrThrow(id);
        customers.remove(id);
    }

    private Customer findOrThrow(UUID id) {
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new NotFoundException("Customer with id " + id + " not found");
        }
        return customer;
    }

    private void checkForEmailDuplication(String email) {
        boolean emailExists = customers.values().stream()
                .anyMatch(existingCustomer -> existingCustomer.getEmail().equalsIgnoreCase(email));

        if (emailExists) {
            throw new IllegalArgumentException("Customer with email " + email + " already exists");
        }
    }
}
