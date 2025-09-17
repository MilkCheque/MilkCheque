package com.whoami.milkcheque.service;

import com.whoami.milkcheque.dto.request.AddMenuItemRequest;
import com.whoami.milkcheque.dto.request.OrderUpdateRequest;
import com.whoami.milkcheque.dto.request.SessionOrdersUpdateRequest;
import com.whoami.milkcheque.dto.response.MenuItemResponse;
import com.whoami.milkcheque.dto.response.StoreInfo;
import com.whoami.milkcheque.dto.response.StoreTableResponse;
import com.whoami.milkcheque.exception.MenuItemRetrievalException;
import com.whoami.milkcheque.exception.SessionOrdersUpdateRequestException;
import com.whoami.milkcheque.exception.StoreInfoRetrievalException;
import com.whoami.milkcheque.exception.StoreTableRetrievalException;
import com.whoami.milkcheque.mapper.Mapper;
import com.whoami.milkcheque.model.CustomerOrderModel;
import com.whoami.milkcheque.model.MenuItemModel;
import com.whoami.milkcheque.model.OrderItemModel;
import com.whoami.milkcheque.model.SessionModel;
import com.whoami.milkcheque.model.StoreModel;
import com.whoami.milkcheque.model.StoreTableModel;
import com.whoami.milkcheque.repository.CustomerOrderRepository;
import com.whoami.milkcheque.repository.MenuItemRepository;
import com.whoami.milkcheque.repository.OrderItemRepository;
import com.whoami.milkcheque.repository.SessionRepository;
import com.whoami.milkcheque.repository.StoreRepository;
import com.whoami.milkcheque.repository.StoreTableRepository;
import com.whoami.milkcheque.validation.StaffRequestValidation;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

  private final MenuItemRepository menuItemRepository;
  private final StoreRepository storeRepository;
  private final StoreTableRepository storeTableRepository;
  private final SessionRepository sessionRepository;
  private final OrderItemRepository orderItemRepository;
  private final CustomerOrderRepository customerOrderRepository;
  private final StaffRequestValidation staffRequestValidation;

  //  private final Mapper mapper;

  //    private final MenuItemDTO menuItemDTO;
  public StoreService(
      MenuItemRepository menuItemRepository,
      StoreRepository storeRepository,
      StoreTableRepository storeTableRepository,
      SessionRepository sessionRepository,
      CustomerOrderRepository customerOrderRepository,
      OrderItemRepository orderItemRepository,
      StaffRequestValidation staffRequestValidation) {
    this.menuItemRepository = menuItemRepository;
    this.storeRepository = storeRepository;
    this.storeTableRepository = storeTableRepository;
    this.sessionRepository = sessionRepository;
    this.orderItemRepository = orderItemRepository;
    this.customerOrderRepository = customerOrderRepository;
    this.staffRequestValidation = staffRequestValidation;
  }

  public ResponseEntity<ArrayList<MenuItemResponse>> getMenuItems(Long storeId) {
    try {
      ArrayList<MenuItemModel> menuItems = menuItemRepository.findByStoreModel_StoreId(storeId);
      ArrayList<MenuItemResponse> menuItemsDTO = new ArrayList<>();
      Mapper mapper = new Mapper();
      if (menuItems.isEmpty()) {
        throw new MenuItemRetrievalException("-1", "Menu items not found (⸝⸝๑﹏๑⸝⸝)");
      }
      for (MenuItemModel menuItem : menuItems) {
        menuItemsDTO.add(mapper.convertMenuItemModelToDto(menuItem));
      }
      return ResponseEntity.status(HttpStatus.OK).body(menuItemsDTO);
    } catch (Exception e) {
      throw new MenuItemRetrievalException("-2", "unexpected error （￣へ￣）" + e.getMessage());
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
    storeTableRepository.activateTable(tableId);
  }

  public ResponseEntity<ArrayList<MenuItemResponse>> getMenuItemByCategory(
      Long storeId, Integer categoryId) {
    try {
      ArrayList<MenuItemModel> menuItems =
          menuItemRepository.findByStoreModel_StoreIdAndMenuItemCategoryId(storeId, categoryId);
      ArrayList<MenuItemResponse> menuItemsDTO = new ArrayList<>();
      Mapper mapper = new Mapper();
      if (menuItems.isEmpty()) {
        throw new MenuItemRetrievalException("-1", "Menu items not found (⸝⸝๑﹏๑⸝⸝)");
      }
      for (MenuItemModel menuItem : menuItems) {
        menuItemsDTO.add(mapper.convertMenuItemModelToDto(menuItem));
      }
      return ResponseEntity.status(HttpStatus.OK).body(menuItemsDTO);

    } catch (Exception e) {
      throw new MenuItemRetrievalException("-2", "unexpected error （￣へ￣）" + e.getMessage());
    }
  }

  public ResponseEntity<ArrayList<StoreTableResponse>> getStoreTables(Long storeId) {
    try {
      if (!storeRepository.existsByStoreId(storeId))
        throw new StoreTableRetrievalException("-1", "Store id does not exist");
      //            MenuItemRepository storeTableRepository;
      ArrayList<StoreTableModel> storeTables =
          storeTableRepository.findByStoreModel_StoreId(storeId);
      ArrayList<StoreTableResponse> storeTablesDTO = new ArrayList<>();
      Mapper mapper = new Mapper();
      if (storeTables.isEmpty()) {
        throw new StoreTableRetrievalException("-2", "Store tables not found");
      }
      for (StoreTableModel storeTable : storeTables) {
        storeTablesDTO.add(mapper.convertStoreTableModelToDto(storeTable));
      }
      return ResponseEntity.status(HttpStatus.OK).body(storeTablesDTO);
    } catch (Exception e) {
      throw new StoreTableRetrievalException("-3", "unexpected error （￣へ￣）" + e.getMessage());
    }
  }

  public ResponseEntity<Boolean> sessionOrdersUpdate(
      SessionOrdersUpdateRequest sessionOrdersUpdateRequest) {
    staffRequestValidation.validateSessionOrdersUpdateRequest(sessionOrdersUpdateRequest);
    try {
      // TODO: Validate token
      Long storeId = sessionOrdersUpdateRequest.getStoreId();
      Long tableId = sessionOrdersUpdateRequest.getTableId();
      SessionModel sessionModel =
          sessionRepository
              .getByStoreModel_StoreIdAndStoreTableModel_StoreTableIdAndIsSessionActiveEquals(
                  storeId, tableId, true);
      for (OrderUpdateRequest updateRequest : sessionOrdersUpdateRequest.getUpdates()) {
        Optional<CustomerOrderModel> customerOrderModel =
            customerOrderRepository.findById(updateRequest.getOrderId());
        if (!customerOrderModel.isPresent()) {
          throw new SessionOrdersUpdateRequestException("-1", "failed to update session order(s)");
        }
        for (Map.Entry<Long, Long> itemEdit : updateRequest.getUpdate().entrySet()) {
          Optional<OrderItemModel> orderItem =
              orderItemRepository.findByMenuItemModel_MenuIdAndCustomerOrderModel_CustomerOrderId(
                  itemEdit.getKey(), updateRequest.getOrderId());
          if (orderItem.isPresent()) {
            OrderItemModel orderItemModel = orderItem.get();
            orderItemModel.updateQuantityDelta(itemEdit.getValue());
            if (orderItemModel.getQuantity() == 0) orderItemRepository.delete(orderItemModel);
            else orderItemRepository.save(orderItemModel);
            continue;
          } else {
            // Assuming it exists
            MenuItemModel menuItemModel = menuItemRepository.getById(itemEdit.getValue());
            OrderItemModel orderItemModel = new OrderItemModel();
            orderItemModel.setCustomerOrderModel(customerOrderModel.get());
            orderItemModel.setMenuItemModel(menuItemModel);
            orderItemModel.setQuantity(itemEdit.getValue());
            orderItemRepository.save(orderItemModel);
          }
        }
      }
      return ResponseEntity.status(HttpStatus.OK).body(true);
    } catch (Exception e) {
      throw new SessionOrdersUpdateRequestException(
          "-1", e.getMessage() + ": failed to update session order(s)");
    }
  }

  public ResponseEntity<Boolean> addMenuItem(AddMenuItemRequest addMenuItemRequest) {
    staffRequestValidation.validateAddMenuItemRequest(addMenuItemRequest);
    Mapper mapper = new Mapper();
    MenuItemModel menuItemModel =
        mapper.convertAddMenuItemRequestToMenuItemModel(addMenuItemRequest);
    StoreModel storeModel = storeRepository.getById(addMenuItemRequest.getStoreId());
    menuItemModel.setStoreModel(storeModel);
    menuItemRepository.save(menuItemModel);
    return ResponseEntity.status(HttpStatus.OK).body(true);
  }
}
