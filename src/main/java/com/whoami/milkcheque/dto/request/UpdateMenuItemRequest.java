package com.whoami.milkcheque.dto.request;

import java.util.HashMap;
import lombok.Data;

@Data
public class UpdateMenuItemRequest {
  private String token; // TODO : Make use of tokens
  private Long menuItemId;
  HashMap<String, String> attributes;
}
