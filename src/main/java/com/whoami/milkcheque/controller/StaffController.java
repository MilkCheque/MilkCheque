package com.whoami.milkcheque.controller;

import com.whoami.milkcheque.model.StaffModel;
import com.whoami.milkcheque.service.StaffService;
import dto.StaffDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @PostMapping("/signup")
    public void signup(@RequestBody StaffDTO  staffDTO) {

        staffService.saveStaff(staffDTO);

    }
}
