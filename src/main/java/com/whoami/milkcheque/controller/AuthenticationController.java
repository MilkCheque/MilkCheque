package com.whoami.milkcheque.controller;

import com.whoami.milkcheque.dto.request.LoginRequest;
import com.whoami.milkcheque.dto.request.SignUpRequest;
import com.whoami.milkcheque.dto.response.LoginResponse;
import com.whoami.milkcheque.dto.response.SignUpResponse;
import com.whoami.milkcheque.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
