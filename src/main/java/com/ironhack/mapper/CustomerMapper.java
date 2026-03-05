package com.ironhack.mapper;

import com.ironhack.dto.request.CustomerRequest;
import com.ironhack.dto.response.CustomerResponse;
import com.ironhack.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toModel(CustomerRequest request) {
        return new Customer(
                request.name(),
                request.email(),
                request.age(),
                request.address()
        );
    }

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAge(),
                customer.getAddress()
        );
    }
}
