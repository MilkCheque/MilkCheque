package com.whoami.milkcheque.dto.response;

import lombok.Data;

@Data
public class CustomerOrderPatchResponse {
    private String code;
    private String message;

    public CustomerOrderPatchResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
