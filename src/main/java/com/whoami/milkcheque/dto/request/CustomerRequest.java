package com.whoami.milkcheque.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate customerDOB;
    private String customerPassword;
    private Long tableId;
    private Long storeId;
}
