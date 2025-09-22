package com.whoami.milkcheque.controller;

import com.whoami.milkcheque.dto.request.CustomerOrderPatchRequest;
import com.whoami.milkcheque.dto.request.CustomerRequest;
import com.whoami.milkcheque.dto.response.CustomerOrderPatchResponse;
import com.whoami.milkcheque.dto.response.CustomerResponse;
import com.whoami.milkcheque.dto.response.OrderResponse;
import com.whoami.milkcheque.service.SessionSerivce;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class SessionController {
  private final SessionSerivce sessionSerivce;

  public SessionController(SessionSerivce sessionSerivce) {
    this.sessionSerivce = sessionSerivce;
  }

  @PostMapping("/add")
  public ResponseEntity<CustomerResponse> addCustomer(
      @RequestBody CustomerRequest customerRequest) {
    return sessionSerivce.addCustomer(customerRequest);
  }

  @PostMapping("/order")
  public ResponseEntity<CustomerOrderPatchResponse> addOrderPatch(
      @RequestBody CustomerOrderPatchRequest customerOrderPatchRequest) {
    return sessionSerivce.addOrderPatch(customerOrderPatchRequest);
  }

  @GetMapping("/allOrders")
  public ResponseEntity<ArrayList<OrderResponse>> getAllOrders(@RequestParam Long sessionId) {
    return sessionSerivce.getAllOrders(sessionId);
  }

  @GetMapping("/cusotmerOrder")
  public ResponseEntity<OrderResponse> getCusotmerOrders(@RequestParam Long orderId) {
    return sessionSerivce.getCustomerOrder(orderId);
  }
}
