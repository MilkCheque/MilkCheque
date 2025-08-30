package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<StoreModel, Long> {
    StoreModel getStoreModelByStoreId(Long storeId);
}
