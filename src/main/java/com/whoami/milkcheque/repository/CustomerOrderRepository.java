package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.CustomerModel;
import com.whoami.milkcheque.model.CustomerOrderModel;
import com.whoami.milkcheque.model.SessionModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrderModel, Long> {

  Optional<CustomerOrderModel> findByCustomerModelAndSessionModel(
      CustomerModel customer, SessionModel session);
}
