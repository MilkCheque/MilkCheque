package com.whoami.milkcheque.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HealthController {
  
    @GetMapping("/health")
    public String healthChecker(){
       return "All good!";
    }
}
