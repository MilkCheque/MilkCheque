package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "CustomerModel")
@Table(name = "customer")
public class CustomerModel {
    //TODO: Use sequence generator
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long CustomerId;

    @Column(
        name = "customer_first_name",
        unique = false,
        nullable = false
    )
    private String customerFirstName;

    @Column(
        name = "customer_last_name",
        unique = false,
        nullable = false
    )
    private String customerLastName;

    @Column(
        name = "customer_phone",
        unique = true,
        nullable = false
    )
    private String customerPhone;

    @Column(
            name="customer_dob",
            unique = false,
            nullable = false
    )
    private LocalDate customerDOB;

    @Column(
        name = "customer_password",
        unique = false,
        nullable = false
    )
    private String customerPassword;

    @OneToMany(
        mappedBy="customerModel",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<CustomerOrderModel> ordersSet = new HashSet<>();

    @ManyToMany(mappedBy = "sessionCustomers")
    private Set<SessionModel> customerSessions = new HashSet<>();
}
