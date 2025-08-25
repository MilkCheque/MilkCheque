package com.whoami.milkcheque.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpRequest {
//    @EMailValidation
    private Long    id;
    private String  name;
    private String  email;
    private LocalDate dateOfBirth;
    private String  phoneNumber;
    private String  password;
}
