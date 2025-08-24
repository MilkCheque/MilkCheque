package com.whoami.milkcheque.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="menu")
public class MenuModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="menu_id")
    private Long menuId;

}
