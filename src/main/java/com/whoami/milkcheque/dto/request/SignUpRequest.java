package com.whoami.milkcheque.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpRequest {
//    @EMailValidation
    private Long    id;
    private String  firstName;
    private String  lastName;
    private String  email;
    private LocalDate DOB;
    private String  phone;
    private String  password;
}
