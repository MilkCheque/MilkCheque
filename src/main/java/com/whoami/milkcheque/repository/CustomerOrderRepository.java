package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.CustomerOrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrderModel, Long> {

}
