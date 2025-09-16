package com.whoami.milkcheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "CustomerModel")
@Table(name = "customer")
public class CustomerModel {
  // TODO: Use sequence generator
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "customer_first_name", unique = false, nullable = true)
  private String customerFirstName;

  @Column(name = "customer_last_name", unique = false, nullable = true)
  private String customerLastName;

  @Column(name = "customer_phone", unique = true, nullable = true)
  private String customerPhone;

  @Column(name = "customer_email", unique = true, nullable = false)
  private String customerEmail;

  @Column(name = "customer_dob", unique = false, nullable = true)
  private LocalDate customerDOB;

  @Column(name = "customer_password", unique = false, nullable = true)
  private String customerPassword;

  @OneToMany(mappedBy = "customerModel", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CustomerOrderModel> ordersSet = new HashSet<>();

  @ManyToMany(mappedBy = "sessionCustomers")
  private Set<SessionModel> customerSessions = new HashSet<>();

  //    @ManyToOne
  //    @JoinColumn(name = "payer_customer_id")
  //    private CustomerModel payer;
  //
  //    @OneToMany(mappedBy = "payer", cascade = CascadeType.ALL, orphanRemoval = false)
  //    private Set<PaymentModel> paymentsMade = new HashSet<>();

  @Override
  public int hashCode() {
    return customerId == null ? 0 : customerId.hashCode();
  }
}
