package com.whoami.milkcheque.service;

import com.whoami.milkcheque.dto.MenuItemDTO;
import com.whoami.milkcheque.dto.StoreInfo;
import com.whoami.milkcheque.exception.MenuItemRetrievalException;
import com.whoami.milkcheque.exception.StoreInfoRetrievalException;
import com.whoami.milkcheque.mapper.MenuItemMapper;
import com.whoami.milkcheque.mapper.StoreInfoMapper;
import com.whoami.milkcheque.model.MenuItemModel;
import com.whoami.milkcheque.model.StoreModel;
import com.whoami.milkcheque.repository.MenuItemRepository;
import com.whoami.milkcheque.repository.MenuRepository;
import com.whoami.milkcheque.repository.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StoreService {

    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    private final StoreRepository storeRepository;

    //    private final MenuItemDTO menuItemDTO;
    public StoreService(MenuRepository menuRepository, MenuItemRepository menuItemRepository, StoreRepository storeRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
        this.storeRepository = storeRepository;
    }

    public ResponseEntity<ArrayList<MenuItemDTO>> getMenuItems(int storeId, int tableId) {
        ArrayList<MenuItemModel> menuItems = menuItemRepository.findByMenuModel_MenuId(12L);
        ArrayList<MenuItemDTO> menuItemsDTO = new ArrayList<>();
        MenuItemMapper mapper = new MenuItemMapper();
        if(menuItems.isEmpty()) {
            throw new MenuItemRetrievalException("Menu items not found");
        }
        for (MenuItemModel menuItem : menuItems) {
            menuItemsDTO.add(mapper.convertToDto(menuItem));
        }

        return ResponseEntity.status(HttpStatus.OK) .body(menuItemsDTO);

    }

    public ResponseEntity<StoreInfo> getStoreInfo(Long storeId) {
        StoreModel storeModel= storeRepository.getStoreModelByStoreId(storeId);
        if(storeModel == null) {
            throw new StoreInfoRetrievalException("Store not found");
        }

        StoreInfoMapper storeInfoMapper = new StoreInfoMapper();
        StoreInfo storeInfo = storeInfoMapper.convertToDto(storeModel);

        return ResponseEntity.status(HttpStatus.OK).body(storeInfo);
    }

}
