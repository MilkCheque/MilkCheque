package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import lombok.Data;

// TODO: Remove Data annotation
@Data
@Entity(name = "MenuItemModel")
@Table(name = "menu_item")
public class MenuItemModel {
  // TODO: Use sequence generator
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "mitem_id")
  private Long menuId;

  @Column(name = "mitem_name", unique = false, nullable = false)
  private String menuItemName;

  @Column(name = "mitem_description", unique = false, nullable = false)
  private String menuItemDescription;

  @Column(name = "mitem_price", unique = false, nullable = false)
  private Double menuItemPrice;

  // TODO: Set to false in production
  @Lob
  @Column(name = "mitem_image", unique = false, nullable = true)
  private String menuItemImage;

  @ManyToOne
  @JoinColumn(name = "store_id")
  private StoreModel storeModel;
}
