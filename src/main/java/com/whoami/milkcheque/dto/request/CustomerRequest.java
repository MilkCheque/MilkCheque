package com.whoami.milkcheque.dto.request;

import lombok.Data;

@Data
public class CustomerRequest {
    private String customerName;
    private String phoneNumber;
    private Long tableId;
}
