package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "store")
public class StoreModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="store_id")
    private Long storeId;
    @Column(name ="store_name")
    private String storeName;
    @Column(name = "store_location")
    private String storeLocation;

    @OneToMany(mappedBy = "storeModel", cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<StaffModel> staffList=new ArrayList<>();

    @OneToMany(mappedBy = "storeModel", cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<TableModel> tableList=new ArrayList<>();

    @OneToMany(mappedBy = "storeModel", cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<MenuModel> menuList=new ArrayList<>();

    @OneToMany(mappedBy = "storeModel", cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<SessionModel> sessionList=new ArrayList<>();

    @OneToMany(mappedBy = "storeModel",cascade = CascadeType.ALL ,orphanRemoval = true )
    private List<MenuItemModel> menuItemList=new ArrayList<>();


}
