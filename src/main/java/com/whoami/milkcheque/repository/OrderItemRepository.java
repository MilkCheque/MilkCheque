package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.OrderItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItemModel, Long> {
}
