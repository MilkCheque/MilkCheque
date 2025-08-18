package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.Customer;
import com.whoami.milkcheque.model.StaffModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<StaffModel, Long> {

}
