package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.LinkedOrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkedOrderRepository extends JpaRepository<LinkedOrderModel, Long> {
  LinkedOrderModel findByMerchantOrderId(Long orderId);
}
