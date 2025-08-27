package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.SessionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<SessionModel, Long> {
    boolean existsByTableModel_StoreTableId(Long storeTableId);
}
