package com.whoami.milkcheque.dto.request;

import java.util.ArrayList;
import lombok.Data;

@Data
public class PaymentRequest {
  Integer amountCents;
  String merchantOrderId;
  String email;
  ArrayList<Long> otherMerchantsOrderId;
}
