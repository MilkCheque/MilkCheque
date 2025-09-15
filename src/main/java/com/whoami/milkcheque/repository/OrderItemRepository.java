package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.OrderItemModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemModel, Long> {
  Optional<OrderItemModel> findByMenuItemModel_MenuIdAndCustomerOrderModel_CustomerOrderId(
      Long menuItemId, Long orderId);
}
