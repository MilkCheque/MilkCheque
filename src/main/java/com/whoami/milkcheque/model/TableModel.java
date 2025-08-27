package com.whoami.milkcheque.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "store_table")
public class TableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="store_table_id")
    private Long storeTableId;
    @Column(name= "is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name="store_id")
    private StoreModel storeModel;

    @OneToMany(mappedBy = "tableModel", cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<SessionModel> sessionList=new ArrayList<>();


}
