package com.suneel.customerservice.controller;

import com.suneel.customerservice.dto.CustomerRequest;
import com.suneel.customerservice.dto.CustomerResponse;
import com.suneel.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<String> createCustomer(
            @RequestBody @Valid CustomerRequest customerRequest
            ){
        String customerId = customerService.createCustomer(customerRequest);
        return ResponseEntity.ok(customerId);
    }


    @PutMapping
    public ResponseEntity<Void> updateCustomer(
            @RequestBody @Valid CustomerRequest customerRequest
    ){
        customerService.updateCustomer(customerRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> getCustomer(
            @PathVariable("customer-id") String customerId
    ){
        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existsById(
            @PathVariable("customer-id") String customerId
    ){
        return ResponseEntity.ok(customerService.existsById(customerId));
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable("customer-id") String customerId
    ){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }

}
