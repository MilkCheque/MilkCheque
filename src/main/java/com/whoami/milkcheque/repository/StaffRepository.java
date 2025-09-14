package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.StaffModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StaffRepository extends JpaRepository<StaffModel, Long> {
  @Query
  Optional<StaffModel> findByStaffEmail(String email);

  @Query
  Optional<StaffModel> findByStaffPhone(String phone);
}
