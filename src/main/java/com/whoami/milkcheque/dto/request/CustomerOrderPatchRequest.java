package com.whoami.milkcheque.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CustomerOrderPatchRequest {
    private String         token; //TODO : Make use of tokens
    private Long           customerId;
    private Long           sessionId;
    private List<Long>  menuItemIds;
}
