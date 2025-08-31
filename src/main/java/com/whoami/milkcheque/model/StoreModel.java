package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
@Entity(name = "StoreModel")
@Table(name = "store")
public class StoreModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "store_name", unique = false, nullable = false)
  private String storeName;

  @Column(name = "store_location", unique = false, nullable = false)
  private String storeLocation;

  @Lob
  @Column(name = "store_logo")
  private String storeLogo;

  @OneToMany(mappedBy = "storeModel", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<StaffModel> staffList = new HashSet<>();

  @OneToMany(mappedBy = "storeModel", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<StoreTableModel> tableList = new HashSet<>();

  @OneToMany(mappedBy = "storeModel", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<MenuItemModel> menuItemsList = new HashSet<>();

  @OneToMany(mappedBy = "storeModel", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<SessionModel> sessionList = new HashSet<>();

  @OneToMany(mappedBy = "storeModel", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<MenuItemModel> menuItemList = new HashSet<>();
}
