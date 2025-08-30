package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "staff")
public class StaffModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="staff_id")
    private Long id;

    @Column(
        name="staff_first_name",
        unique = false,
        nullable = false
    )
    private String staffFirstName;

    @Column(
        name="staff_last_name",
        unique = false,
        nullable = false
    )
    private String staffLastName;

    @Column(
        name="staff_email",
        unique = true,
        nullable = false
    )
    private String staffEmail;

    @Column(
        name="staff_dob",
        unique = false,
        nullable = false
    )
    private LocalDate staffDOB;

    @Column(
        name="staff_password",
        unique = false,
        nullable = false
    )
    private String staffPassword;

    @Column(
        name="staff_phone",
        unique = true,
        nullable = false
    )
    private String staffPhone;

    @ManyToOne
    @JoinColumn(name="store_id")
    private StoreModel storeModel;


}
