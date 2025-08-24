package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import lombok.Data;

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
}
