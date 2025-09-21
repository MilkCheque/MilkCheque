package com.whoami.milkcheque.dto.request;

import java.util.ArrayList;
import lombok.Data;

@Data
public class PaymentRequest {
  Integer amountCents;
  String merchantOrderId;
  ArrayList<Long> otherMerchantsOrderId;
}
