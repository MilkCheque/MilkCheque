package com.whoami.milkcheque.dto.request;

import java.util.HashMap;
import lombok.Data;

@Data
public class OrderUpdateRequest {
  /*
   * This includes the per order updates requested.
   *
   * OrderId: OrderId to be updated
   * update: [OrderItemId, DeltaChange]
   */
  private Long orderId;
  private HashMap<Long, Long> update;
}
