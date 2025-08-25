package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.MenuItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemModel, Long> {

    ArrayList<MenuItemModel> findByMenuModel_MenuId(Long menuId);
}
