package com.whoami.milkcheque.repository;

import com.whoami.milkcheque.model.MenuModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuModel, Long> {
}
