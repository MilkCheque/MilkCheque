package com.whoami.milkcheque.mapper;

import com.whoami.milkcheque.dto.MenuItemDTO;
import com.whoami.milkcheque.model.MenuItemModel;

import java.awt.*;

public class MenuItemMapper {

    public MenuItemDTO convertToDto(MenuItemModel menuItemModel) {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setMenuItemId(menuItemModel.getMenuId());
        menuItemDTO.setMenuItemName(menuItemModel.getMenuItemName());
        menuItemDTO.setMenuItemDescription(menuItemModel.getMenuItemDescription());
        menuItemDTO.setPrice(menuItemModel.getMenuItemPrice());
        return menuItemDTO;
    }

    public MenuItemModel convertToModel(MenuItemDTO menuItemDTO) {
        MenuItemModel menuItemModel = new MenuItemModel();
        menuItemModel.setMenuId(menuItemDTO.getMenuItemId());
        menuItemModel.setMenuItemName(menuItemDTO.getMenuItemName());
        menuItemModel.setMenuItemDescription(menuItemDTO.getMenuItemDescription());
        menuItemModel.setMenuItemPrice(menuItemDTO.getPrice());
        return menuItemModel;
    }
}
