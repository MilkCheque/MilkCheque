package com.whoami.milkcheque.dto.response;

import lombok.Data;

@Data
public class SignUpResponse {
    private String code;
    private String message;

    public SignUpResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
