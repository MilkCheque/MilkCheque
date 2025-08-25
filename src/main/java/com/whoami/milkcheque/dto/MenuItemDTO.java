package com.whoami.milkcheque.dto;


import lombok.Data;

@Data
public class MenuItemDTO {
    private Long menuItemId;
    private String menuItemName;
    private Double price;
    private String menuItemDescription;
    private Byte[] menuItemIcon;

}
