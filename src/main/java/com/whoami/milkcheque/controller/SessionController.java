package com.whoami.milkcheque.controller;


import com.whoami.milkcheque.dto.request.CustomerRequest;
import com.whoami.milkcheque.dto.response.LoginResponse;
import com.whoami.milkcheque.model.CustomerModel;
import com.whoami.milkcheque.service.SessionSerivce;
import jakarta.websocket.Session;
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
    public ResponseEntity<LoginResponse> addCustomer(@RequestBody CustomerRequest customerRequest) {
        return sessionSerivce.addCustomer(customerRequest);

    }

}
