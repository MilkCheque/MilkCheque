package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItemModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_item_id")
  private String orderItemId;

  @ManyToOne
  @JoinColumn(name = "customer_order_id")
  private CustomerOrderModel customerOrderModel;

  @ManyToOne
  @JoinColumn(name = "mitem_id")
  private MenuItemModel menuItemModel;

  @Column(name = "quantity", nullable = false)
  private Long quantity;

  public void updateQuantityDelta(Long delta) {
    quantity += delta;
  }
}
