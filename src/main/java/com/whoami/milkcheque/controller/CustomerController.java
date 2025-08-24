package com.whoami.milkcheque.controller;

import com.whoami.milkcheque.model.CustomerModel;
import com.whoami.milkcheque.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/add")
    public CustomerModel createCustomer(@RequestBody CustomerModel customer) {
        return customerRepository.save(customer);
    }
}
