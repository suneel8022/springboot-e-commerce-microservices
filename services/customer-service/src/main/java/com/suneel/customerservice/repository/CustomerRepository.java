package com.suneel.customerservice.repository;

import com.suneel.customerservice.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer,String> {
    Optional<Customer> findById(String id);
}
