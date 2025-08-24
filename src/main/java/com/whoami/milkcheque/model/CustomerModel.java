package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @OneToMany(mappedBy="customerModel",cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<CustomerOrderModel> CustomerOrderList=new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "customer_order",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id")
    )
    private Set<SessionModel> customerSessions = new HashSet<>();

}


