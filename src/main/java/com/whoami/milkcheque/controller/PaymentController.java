package com.whoami.milkcheque.controller;

import com.whoami.milkcheque.dto.request.PaymentRequest;
import com.whoami.milkcheque.service.PaymentService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping("/pay")
  public ResponseEntity<String> pay(@RequestBody PaymentRequest paymentRequest) {
    return paymentService.paymob(paymentRequest);
  }

  @PostMapping("/callback")
  public ResponseEntity<String> handlePaymentCallback(@RequestBody Map<String, Object> payload) {
    return paymentService.processCallBack(payload);
  }
}
