package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="order_item")
public class OrderItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private String orderItemId;

    @ManyToOne
    @JoinColumn(name="customer_order_id")
    private CustomerOrderModel customerOrderModel;

    @ManyToOne
    @JoinColumn(name="mitem_id")
    private MenuItemModel menuItemModel;

    @Column(
      name = "quantity",
      nullable = false
    )
    private Long quantity;
}
