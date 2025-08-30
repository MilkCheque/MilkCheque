package com.whoami.milkcheque.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "SessionModel")
@Table(name = "session")
public class SessionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long sessionId;

    @ManyToOne
    @JoinColumn(name="store_id")
    private StoreModel storeModel;

    @ManyToOne
    @JoinColumn(name="store_table_id")
    private StoreTableModel storeTableModel;

    @OneToMany(
        mappedBy="sessionModel",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<CustomerOrderModel> CustomerOrderList = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "session_customer",
        joinColumns = @JoinColumn(
            name = "session_id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "customer_id"
        )
    )
    private Set<CustomerModel> sessionCustomers = new HashSet<>();
}
