package com.whoami.milkcheque.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
public class CredentialsDTO {
    private String email;
    private String password;
}
