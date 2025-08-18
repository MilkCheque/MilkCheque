package com.whoami.milkcheque.controller;

import com.whoami.milkcheque.model.Customer;
import com.whoami.milkcheque.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/add")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }
}
