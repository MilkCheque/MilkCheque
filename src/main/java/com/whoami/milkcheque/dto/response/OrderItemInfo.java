package com.whoami.milkcheque.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemInfo {
  private String name;
  private Long quantity;
  private Double price;

  public OrderItemInfo(String name, Long quantity, Double price) {
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }
}
