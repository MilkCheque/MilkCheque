package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.StaffModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<StaffModel, Long> {
  @Query
  Optional<StaffModel> findByStaffEmail(String email);
  @Query
  Optional<StaffModel> findByStaffPhone(String phone);
}
