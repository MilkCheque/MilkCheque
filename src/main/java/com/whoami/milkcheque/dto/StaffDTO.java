package com.whoami.milkcheque.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StaffDTO {
    private Long    id;
    private String  name;
    private String  email;
    private LocalDate dateOfBirth;
    private String  phoneNumber;
    private String  password;
}
