package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "LinkedOrderModel")
@Table(name = "linked_order")
public class LinkedOrderModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long Id;

  @Column(name = "order_id")
  private Long merchantOrderId;

  @Column(name = "linked_order_id")
  private String linkedOrdersId;

  public LinkedOrderModel(String merchantOrderId, String string) {
    this.merchantOrderId = Long.parseLong(string);
    this.linkedOrdersId = merchantOrderId;
  }

  public LinkedOrderModel() {}
}
