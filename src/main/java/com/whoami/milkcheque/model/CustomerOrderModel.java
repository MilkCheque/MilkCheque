package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "CustomerOrderModel")
@Table(name = "customer_order")
public class CustomerOrderModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "customer_order_id")
  private Long customerOrderId;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private CustomerModel customerModel;

  @ManyToOne
  @JoinColumn(name = "session_id")
  private SessionModel sessionModel;

  @OneToMany(mappedBy = "customerOrderModel", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<OrderItemModel> orderItemsSet = new HashSet<>();

  @OneToMany(mappedBy = "customerOrderModel", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<PaymentModel> paymentSet = new HashSet<>();
}
