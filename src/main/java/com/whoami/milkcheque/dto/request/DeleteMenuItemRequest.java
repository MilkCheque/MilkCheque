package com.whoami.milkcheque.dto.request;

import lombok.Data;

@Data
public class DeleteMenuItemRequest {
  private String token; // TODO : Make use of tokens
  private Long menuItemId;
}
