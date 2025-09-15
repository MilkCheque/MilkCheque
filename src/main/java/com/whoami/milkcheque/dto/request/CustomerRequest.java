package com.whoami.milkcheque.dto.request;

import lombok.Data;

@Data
public class CustomerRequest {
  private String firstName;
  private String customerEmail;
  private Long tableId;
  private Long storeId;
}
