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
    private Long StoreId;
    @Column(name ="store_name")
    private String storeName;
    @Column(name = "store_location")
    private String storeLocation;

    @OneToMany(mappedBy = "storeModel", cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<StaffModel> staffList=new ArrayList<>();
    @OneToMany(mappedBy = "storeModel", cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<TableModel> TableList=new ArrayList<>();
    @OneToMany(mappedBy = "storeModel", cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<MenuModel> MenuList=new ArrayList<>();
    @OneToMany(mappedBy = "storeModel", cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<SessionModel> SessionList=new ArrayList<>();


}
