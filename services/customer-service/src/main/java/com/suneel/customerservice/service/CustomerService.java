package com.suneel.customerservice.service;

import com.suneel.customerservice.dto.CustomerRequest;
import com.suneel.customerservice.dto.CustomerResponse;
import com.suneel.customerservice.entity.Customer;
import com.suneel.customerservice.exception.CustomerNotFoundException;
import com.suneel.customerservice.mapper.CustomerMapper;
import com.suneel.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest customerRequest){
        var customer = customerRepository.save(customerMapper.toEntity(customerRequest));
        return customer.getId();
    }


    public void updateCustomer(CustomerRequest customerRequest){
        var customer = customerRepository.findById(customerRequest.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                    String.format("Cannot update customer :: No customer found with ID : %s", customerRequest.id())
                ));

        merger(customerRequest, customer);

    }

    public void merger(CustomerRequest customerRequest, Customer customer){
        if(StringUtils.isNotBlank(customerRequest.firstName())){
            customer.setFirstName(customerRequest.firstName());
        }
        if(StringUtils.isNotBlank(customerRequest.email())){
            customer.setEmail(customerRequest.email());
        }
        if(customerRequest.address() != null){
            customer.setAddress(customerRequest.address());
        }
    }

    public List<CustomerResponse> getAllCustomers(){
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomer(String id){
        return customerRepository.findById(id)
                .map(customerMapper::toResponse)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("No customer found with id: %s", id)
                ));
    }

    public boolean existsById(String id){
        return customerRepository.existsById(id);
    }

    public void deleteCustomer(String id){
        customerRepository.deleteById(id);
    }

}
