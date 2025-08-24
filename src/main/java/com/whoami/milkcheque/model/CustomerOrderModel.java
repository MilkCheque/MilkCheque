package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="customer_order")
public class CustomerOrderModel {
    @Id
    @Column(name = "customer_order_id")
    private String customerOrderId;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private CustomerModel customerModel;

    @ManyToOne
    @JoinColumn(name="session_id")
    private SessionModel sessionModel;

    @ManyToMany
    @JoinTable(
            name = "order_item",
            joinColumns = @JoinColumn(name = "customer_order_id"),
            inverseJoinColumns = @JoinColumn(name = "mitem_id")
    )
    private Set<MenuItemModel> menuItems = new HashSet<>();

}
