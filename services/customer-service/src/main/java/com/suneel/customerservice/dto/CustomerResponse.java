package com.suneel.customerservice.dto;

import com.suneel.customerservice.entity.Address;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
){}
