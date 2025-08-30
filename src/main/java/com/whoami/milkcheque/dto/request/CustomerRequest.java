package com.whoami.milkcheque.dto.request;

import lombok.Data;

@Data
public class CustomerRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private Long tableId;
}
