package com.whoami.milkcheque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="customer_order")
public class CustomerOrderModel {
    @Id
    @Column(name = "customer_order_id")
    private String customerOrderId;
}
