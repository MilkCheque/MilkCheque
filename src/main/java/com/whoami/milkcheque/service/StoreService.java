package com.whoami.milkcheque.service;

import com.whoami.milkcheque.dto.response.MenuItemResponse;
import com.whoami.milkcheque.dto.response.StoreInfo;
import com.whoami.milkcheque.exception.MenuItemRetrievalException;
import com.whoami.milkcheque.exception.StoreInfoRetrievalException;
import com.whoami.milkcheque.mapper.Mapper;
import com.whoami.milkcheque.model.MenuItemModel;
import com.whoami.milkcheque.model.StoreModel;
import com.whoami.milkcheque.repository.MenuItemRepository;
import com.whoami.milkcheque.repository.StoreRepository;
import com.whoami.milkcheque.repository.StoreTableRepository;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

  private final MenuItemRepository menuItemRepository;
  private final StoreRepository storeRepository;
  private final StoreTableRepository tableRepository;

  //    private final MenuItemDTO menuItemDTO;
  public StoreService(
      MenuItemRepository menuItemRepository,
      StoreRepository storeRepository,
      StoreTableRepository tableRepository) {
    this.menuItemRepository = menuItemRepository;
    this.storeRepository = storeRepository;
    this.tableRepository = tableRepository;
  }

  public ResponseEntity<ArrayList<MenuItemResponse>> getMenuItems(Long storeId) {
    try {
      ArrayList<MenuItemModel> menuItems = menuItemRepository.findByStoreModel_StoreId(storeId);
      ArrayList<MenuItemResponse> menuItemsDTO = new ArrayList<>();
      Mapper mapper = new Mapper();
      if (menuItems.isEmpty()) {
        throw new MenuItemRetrievalException("Menu items not found (⸝⸝๑﹏๑⸝⸝)");
      }
      for (MenuItemModel menuItem : menuItems) {
        menuItemsDTO.add(mapper.convertMenuItemModelToDto(menuItem));
      }
      return ResponseEntity.status(HttpStatus.OK).body(menuItemsDTO);
    } catch (Exception e) {
      throw new MenuItemRetrievalException("unexpected error （￣へ￣）" + e.getMessage());
    }
  }

  public ResponseEntity<StoreInfo> getStoreInfo(Long storeId, Long tableId) {
    try {
      StoreModel storeModel = storeRepository.getStoreModelByStoreId(storeId);
      if (storeModel == null) {
        throw new StoreInfoRetrievalException("Store not found (⸝⸝๑﹏๑⸝⸝)");
      }
      Mapper mapper = new Mapper();
      StoreInfo storeInfo = mapper.convertStoreModelToStoreInfoDto(storeModel, tableId);
      activateTable(tableId);
      return ResponseEntity.status(HttpStatus.OK).body(storeInfo);

    } catch (Exception e) {
      throw new StoreInfoRetrievalException("Unexpected error （￣へ￣）" + e.getMessage());
    }
  }

  public void activateTable(Long tableId) {
    tableRepository.activateTable(tableId);
  }
}
