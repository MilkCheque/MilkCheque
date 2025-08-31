package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.SessionModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<SessionModel, Long> {
  Optional<SessionModel> findByStoreTableModel_StoreTableId(Long storeTableId);

  SessionModel getByStoreTableModel_StoreTableId(Long storeTableId);

  boolean existsByStoreTableModel_StoreTableId(Long storeTableId);
}
