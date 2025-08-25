package com.whoami.milkcheque.service;

import com.whoami.milkcheque.dto.MenuItemDTO;
import com.whoami.milkcheque.model.MenuItemModel;
import com.whoami.milkcheque.repository.MenuItemRepository;
import com.whoami.milkcheque.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    public MenuService(MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public Optional<MenuItemDTO> getMenuItems(int storeId, int tableId) {
        Optional<MenuItemDTO>  menuItems=this.menuItemRepository.findByMenuId(1);

        return  menuItems.map();

    }

}
