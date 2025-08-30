package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.CustomerOrderModel;
import com.whoami.milkcheque.model.CustomerModel;
import com.whoami.milkcheque.model.SessionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrderModel, Long> {

    Optional<CustomerOrderModel> findByCustomerModelAndSessionModel(CustomerModel customerId, SessionModel sessionId);
}
