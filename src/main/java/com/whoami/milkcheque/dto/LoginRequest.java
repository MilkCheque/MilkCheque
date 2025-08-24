package com.whoami.milkcheque.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
