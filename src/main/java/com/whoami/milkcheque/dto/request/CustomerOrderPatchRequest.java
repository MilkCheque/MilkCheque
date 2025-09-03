package com.whoami.milkcheque.dto.request;

import java.util.HashMap;
import lombok.Data;

@Data
public class CustomerOrderPatchRequest {
  private String token; // TODO : Make use of tokens
  private Long customerId;
  private Long sessionId;
  private Long storeId;
  private HashMap<Long, Long> orderItems;
}
