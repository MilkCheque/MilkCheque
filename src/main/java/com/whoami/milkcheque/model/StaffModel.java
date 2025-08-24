package com.whoami.milkcheque.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "staff")
public class StaffModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="staff_id")
    private Long id;

    @Column(name="staff_name")
    private String name;

    @Column(name="staff_email")
    private String email;
    @Column(name="staff_dob")
    private LocalDate dateOfBirth;
    @Column(name="staff_password")
    private String password;
    @Column(name="staff_phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name="store_id")
    private StoreModel storeModel;


}
