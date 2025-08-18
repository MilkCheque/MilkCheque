package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;


}


