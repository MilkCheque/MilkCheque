package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreModel, Long> {
}
