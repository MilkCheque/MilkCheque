package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
}
