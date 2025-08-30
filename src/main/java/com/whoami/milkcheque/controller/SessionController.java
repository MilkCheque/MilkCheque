package com.whoami.milkcheque.controller;

import com.whoami.milkcheque.dto.request.CustomerRequest;
import com.whoami.milkcheque.dto.response.LoginResponse;
import com.whoami.milkcheque.dto.request.CustomerOrderPatchRequest;
import com.whoami.milkcheque.dto.response.CustomerOrderPatchResponse;
import com.whoami.milkcheque.service.SessionSerivce;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {
    private final SessionSerivce sessionSerivce;
    public SessionController(SessionSerivce sessionSerivce) {
        this.sessionSerivce = sessionSerivce;
    }

    @PostMapping("/add")
    public ResponseEntity<LoginResponse> addCustomer(
            @RequestBody CustomerRequest customerRequest) {
        return sessionSerivce.addCustomer(customerRequest);
    }

    @PostMapping("/order")
    public ResponseEntity<CustomerOrderPatchResponse> addOrderPatch(
            @RequestBody CustomerOrderPatchRequest customerOrderPatchRequest) {
        return sessionSerivce.addOrderPatch(customerOrderPatchRequest);
    }
}
