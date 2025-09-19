package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "payment")
@Entity(name = "PaymentModel")
public class PaymentModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "payment_id", nullable = false)
  private Long paymentId;

  @Column(name = "amount_cents", nullable = false)
  private int amountCents;

  @Column(name = "currency", nullable = false)
  private String currency = "EGP";

  @Column(name = "status", nullable = false)
  private String status = "pending";

  @Column(name = "payment_token")
  private String paymentToken;

  @Column(name = "transaction_id")
  private String transactionId;

  @Column(
      name = "created_at",
      updatable = false,
      insertable = false,
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

  @Column(
      name = "updated_at",
      insertable = false,
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  private Timestamp updatedAt;

  @ManyToOne
  @JoinColumn(name = "customer_order_id", nullable = true)
  private CustomerOrderModel customerOrderModel;

  @Column(name = "linked_orders")
  private String linkedOrders;

  //    @ManyToOne
  //    @JoinColumn(name = "payer_customer_id", nullable = true)
  //    private CustomerModel payerCustomer;
}
