package com.whoami.milkcheque.controller;

import jakarta.websocket.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  
    @PostMapping("/Test")
    public String test(){
       return "POST works!"; 
    }
}
