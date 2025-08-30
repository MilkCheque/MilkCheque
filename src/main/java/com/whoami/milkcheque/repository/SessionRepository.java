package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.SessionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<SessionModel, Long> {
    Optional<SessionModel> findByStoreTableModel_StoreTableId(Long storeTableId);
    SessionModel getByStoreTableModel_StoreTableId(Long storeTableId);
    boolean existsByStoreTableModel_StoreTableId(Long storeTableId);
}
