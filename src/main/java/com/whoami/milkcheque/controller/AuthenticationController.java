package com.whoami.milkcheque.controller;

import com.whoami.milkcheque.model.StaffModel;
import com.whoami.milkcheque.service.AuthenticationService;
import com.whoami.milkcheque.dto.SignUpRequest;
import com.whoami.milkcheque.dto.SignUpResponse;
import com.whoami.milkcheque.dto.LoginRequest;
import com.whoami.milkcheque.dto.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    private AuthenticationController(AuthenticationService authenticationService) {
      this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpRequest signUpRequest) {
        return authenticationService.signUpProcess(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return authenticationService.loginProcess(loginRequest);
    }
}
