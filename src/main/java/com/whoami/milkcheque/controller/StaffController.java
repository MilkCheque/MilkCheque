package com.whoami.milkcheque.controller;

import com.whoami.milkcheque.model.StaffModel;
import com.whoami.milkcheque.service.StaffService;
import com.whoami.milkcheque.dto.StaffDTO;
import com.whoami.milkcheque.dto.CredentialsDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/auth")
public class StaffController {
    @Autowired
    private StaffService staffService;
    //

    @PostMapping("/signup")
    public void signup(@RequestBody StaffDTO staffDTO) {
        staffService.saveStaff(staffDTO);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody CredentialsDTO credentialsDTO) {
      if (!staffService.checkCredential(credentialsDTO))
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Authentication failed.");
      return ResponseEntity.ok("Welcome!");
    }
}
