package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "menu_item")
public class MenuItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mitem_id")
    private Long menuId;
    @Column(name="mitem_name")
    private String menuItemName;
    @Column(name="mitem_price")
    private Double menuItemPrice;
    @Column(name = "mitem_description")
    private String menuItemDescription;
    @Lob
    @Column(name = "mitem_image")
    private byte[] menuItemImage;

}
