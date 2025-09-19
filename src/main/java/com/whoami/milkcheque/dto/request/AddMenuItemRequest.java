package com.whoami.milkcheque.dto.request;

import lombok.Data;

@Data
public class AddMenuItemRequest {
  private String token; // TODO : Make use of tokens
  private String name;
  private String description;
  private Double price;
  private String pictureURL;
  private Integer CategoryId;
  private Long storeId;
}
