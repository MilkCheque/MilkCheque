package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class CustomerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long CustomerId;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_phone_number")
    private String customerPhoneNumber;
}


