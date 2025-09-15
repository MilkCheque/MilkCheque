package com.whoami.milkcheque.dto.response;

import lombok.Data;

@Data
public class MenuItemResponse {
  private Long menuItemId;
  private String menuItemName;
  private Double price;
  private String menuItemDescription;
  private Integer menuItemCategory;
  private String menuItemPictureURL;
}
