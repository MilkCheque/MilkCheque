package com.whoami.milkcheque.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class HealthController {
  @GetMapping("/health")
  public String healthChecker() {
    return "All Good!";
  }

  @GetMapping("/health2")
  public String healthChecker2() {
    return "Still good!";
  }
}
