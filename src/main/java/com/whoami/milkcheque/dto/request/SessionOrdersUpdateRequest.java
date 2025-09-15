package com.whoami.milkcheque.dto.request;

import java.util.ArrayList;
import lombok.Data;

@Data
public class SessionOrdersUpdateRequest {
  private Long token;
  private Long storeId;
  private Long tableId;
  ArrayList<OrderUpdateRequest> updates;
}
