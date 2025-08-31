package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
@Entity(name = "StoreTableModel")
@Table(name = "store_table")
public class StoreTableModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "store_table_id")
  private Long storeTableId;

  @ManyToOne
  @JoinColumn(name = "store_id")
  private StoreModel storeModel;

  @Column(name = "is_active", nullable = false, columnDefinition = "0")
  private Boolean isActive;

  @OneToMany(mappedBy = "storeTableModel", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<SessionModel> sessionList = new HashSet<>();
}
