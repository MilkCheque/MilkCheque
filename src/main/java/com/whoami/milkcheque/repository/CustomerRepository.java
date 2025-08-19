package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
