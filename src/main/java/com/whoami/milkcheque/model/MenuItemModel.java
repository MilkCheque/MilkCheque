package com.whoami.milkcheque.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name="menu_id")
    @JsonIgnore
    private MenuModel menuModel;

    @ManyToMany(mappedBy = "menuItems")
    private Set<CustomerOrderModel> customerOrders = new HashSet<>();

}
