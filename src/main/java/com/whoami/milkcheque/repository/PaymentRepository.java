package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, Long> {}
