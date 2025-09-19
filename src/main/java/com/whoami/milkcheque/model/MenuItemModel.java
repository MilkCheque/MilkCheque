package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

  @Column(name = "mitem_URL ", unique = false, nullable = false)
  private String menuItemPictureURL;

  @Column(name = "mitem_category", unique = false, nullable = false)
  private Integer menuItemCategoryId;

  @ManyToOne
  @JoinColumn(name = "store_id")
  private StoreModel storeModel;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MenuItemModel)) return false;
    MenuItemModel other = (MenuItemModel) o;
    return menuId != null && menuId.equals(other.getMenuId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(menuId);
  }
}
