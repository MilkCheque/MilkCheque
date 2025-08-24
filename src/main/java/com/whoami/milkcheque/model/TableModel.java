package com.whoami.milkcheque.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "store_table")
public class TableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="store_table_id")
    private Long tableId;

}
