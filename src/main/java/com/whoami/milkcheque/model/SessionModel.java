package com.whoami.milkcheque.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "session")
public class SessionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long sessionId;
    @ManyToOne
    @JoinColumn(name="store_id")
    private StoreModel storeModel;
//    @ManyToOne
//    @JoinColumn(name="menu_id")
//    private MenuModel menuModel;
    @ManyToOne
    @JoinColumn(name="store_table_id")
    private TableModel tableModel;
    @OneToMany(mappedBy="sessionModel",cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<CustomerOrderModel> CustomerOrderList=new ArrayList<>();

    @ManyToMany(mappedBy = "customerSessions")
    @JsonIgnore
    private Set<CustomerModel> sessionSet=new HashSet<>();

    public void setCutomerModel(Long customerId) {
    }
}
