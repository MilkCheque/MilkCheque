package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.MenuItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItemModel, Long> {
    @Query
    Optional<MenuItemModel> findByMenuId(int menuId);
}
