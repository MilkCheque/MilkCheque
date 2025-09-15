package com.whoami.milkcheque.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonPropertyOrder({"customerId", "customerName", "orderItems"})
public class AllOrdersResponse {

  private Long customerId;
  private String customerName;
  ArrayList<OrderItemInfo> orderItems;

  public AllOrdersResponse(
      Long customerId, String customerFirstName, ArrayList<OrderItemInfo> orderItems) {
    this.customerId = customerId;
    this.customerName = customerFirstName;
    this.orderItems = orderItems;
  }
}
