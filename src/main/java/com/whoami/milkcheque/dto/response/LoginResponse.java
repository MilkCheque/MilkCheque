package com.whoami.milkcheque.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String code;
    private String message;
    private String token;

    public LoginResponse(String code, String message, String token) {
        this.code = code;
        this.message = message;
        this.token = token;
    }
}
