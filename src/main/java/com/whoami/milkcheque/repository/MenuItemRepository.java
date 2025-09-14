package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.MenuItemModel;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemModel, Long> {

  ArrayList<MenuItemModel> findByStoreModel_StoreId(Long storeId);

  ArrayList<MenuItemModel> findByStoreModel_StoreIdAndMenuItemCategory(
      Long storeId, String category);
}
