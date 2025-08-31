package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.StoreTableModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreTableRepository extends JpaRepository<StoreTableModel, Long> {
  @Modifying
  @Transactional
  @Query(
      nativeQuery = true,
      value = "update store_table t set t.is_active=true where t.store_table_id=:storeTableId ")
  void activateTable(@Param("storeTableId") Long storeTableId);

  StoreTableModel findByStoreTableId(Long tableId);
}
