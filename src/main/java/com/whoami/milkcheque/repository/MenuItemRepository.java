package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.MenuItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItemModel, Long> {
}
