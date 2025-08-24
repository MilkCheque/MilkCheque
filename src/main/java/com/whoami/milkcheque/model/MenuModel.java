package com.whoami.milkcheque.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="menu")
public class MenuModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="menu_id")
    private Long menuId;
    @OneToMany(mappedBy="menuModel",cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<MenuItemModel> MenuItemList=new ArrayList<>();
    @ManyToOne
    @JoinColumn(name="store_id")
    private StoreModel storeModel;

}
