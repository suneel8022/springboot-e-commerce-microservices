package com.suneel.customerservice.mapper;

import com.suneel.customerservice.dto.CustomerRequest;
import com.suneel.customerservice.dto.CustomerResponse;
import com.suneel.customerservice.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequest customerRequest){
        if(customerRequest == null){
            return null;
        }

        return Customer.builder()
                .id(customerRequest.id())
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .address(customerRequest.address())
                .build();
    }


    public CustomerResponse toResponse(Customer customer){
        if(customer == null){
            return null;
        }
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
