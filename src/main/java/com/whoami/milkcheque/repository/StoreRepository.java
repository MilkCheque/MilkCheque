package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.dto.StoreInfo;
import com.whoami.milkcheque.model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoreRepository extends JpaRepository<StoreModel, Long> {
    @Query
    StoreModel getStoreModelByStoreId(Long storeId);
}
