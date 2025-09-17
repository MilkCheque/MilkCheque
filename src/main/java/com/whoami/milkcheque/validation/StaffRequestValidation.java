package com.whoami.milkcheque.validation;

import com.whoami.milkcheque.dto.request.AddMenuItemRequest;
import com.whoami.milkcheque.dto.request.DeleteMenuItemRequest;
import com.whoami.milkcheque.dto.request.OrderUpdateRequest;
import com.whoami.milkcheque.dto.request.SessionOrdersUpdateRequest;
import com.whoami.milkcheque.dto.request.UpdateMenuItemRequest;
import com.whoami.milkcheque.exception.MenuItemUpdateException;
import com.whoami.milkcheque.exception.SessionOrdersUpdateRequestException;
import com.whoami.milkcheque.model.CustomerOrderModel;
import com.whoami.milkcheque.model.OrderItemModel;
import com.whoami.milkcheque.model.StoreModel;
import com.whoami.milkcheque.model.StoreTableModel;
import com.whoami.milkcheque.repository.CustomerOrderRepository;
import com.whoami.milkcheque.repository.OrderItemRepository;
import com.whoami.milkcheque.repository.StoreRepository;
import com.whoami.milkcheque.repository.StoreTableRepository;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StaffRequestValidation {
  private final StoreRepository storeRepository;
  private final StoreTableRepository storeTableRepository;
  private final CustomerOrderRepository customerOrderRepository;
  private final OrderItemRepository orderItemRepository;

  StaffRequestValidation(
      StoreRepository storeRepository,
      StoreTableRepository storeTableRepository,
      OrderItemRepository orderItemRepository,
      CustomerOrderRepository customerOrderRepository) {
    this.storeRepository = storeRepository;
    this.storeTableRepository = storeTableRepository;
    this.customerOrderRepository = customerOrderRepository;
    this.orderItemRepository = orderItemRepository;
  }

  // TODO: Add more descripitive exception messages
  public void validateSessionOrdersUpdateRequest(
      SessionOrdersUpdateRequest sessionOrdersUpdateRequest) {
    /*
     * 1. TODO: validate token
     * 2. Validate storeId exists
     * 3. Validate tableId exists with $storeId equal to storeId
     * 4. Validate updates
     */
    if (!storeRepository.existsById(sessionOrdersUpdateRequest.getStoreId())) {
      throw new SessionOrdersUpdateRequestException("-1", "store id does not exist");
    }
    Optional<StoreTableModel> storeTable =
        storeTableRepository.findById(sessionOrdersUpdateRequest.getTableId());
    if (!storeTable.isPresent()) {
      throw new SessionOrdersUpdateRequestException("-2", "table id does not exist");
    }
    if (storeTable.get().getStoreModel().getStoreId() != sessionOrdersUpdateRequest.getStoreId()) {
      throw new SessionOrdersUpdateRequestException("-3", "table id does not exist in store id");
    }
    sessionOrdersUpdateRequest.getUpdates().forEach(update -> validateUpdate(update));
  }

  private void validateUpdate(OrderUpdateRequest update) {
    /*
     * 1. Validate orderId
     * 2. Validate orderItemId exist
     * 3. Validate the delta quantity will not yield negative
     */
    Optional<CustomerOrderModel> customerOrder =
        customerOrderRepository.findById(update.getOrderId());
    if (!customerOrder.isPresent()) {
      throw new SessionOrdersUpdateRequestException("-4", "customerOrder doesn't exist");
    }
    for (Map.Entry<Long, Long> update_item : update.getUpdate().entrySet()) {
      Optional<OrderItemModel> orderItem = orderItemRepository.findById(update_item.getKey());
      if (!orderItem.isPresent()) {
        throw new SessionOrdersUpdateRequestException("-5", "orderItemId doesn't exist");
      }
      if (orderItem.get().getQuantity() + update_item.getValue() < 0) {
        throw new SessionOrdersUpdateRequestException("-6", "no sufficient items to be removed");
      }
    }
  }

  public void validateAddMenuItemRequest(AddMenuItemRequest addMenuItemRequest) {
    Optional<StoreModel> storeModelOpt = storeRepository.findById(addMenuItemRequest.getStoreId());
    if (!storeModelOpt.isPresent())
      throw new MenuItemUpdateException("-1", "store id does not exist");
  }

  public void validateUpdateMenuItemRequest(UpdateMenuItemRequest updateMenuItemRequest) {
    // TODO: NOT IMPLEMENTED
    log.warn("validateUpdateMenuItemRequest NOT IMPLEMENTED");
  }

  public void validateDeleteMenuItemRequest(DeleteMenuItemRequest deleteMenuItemRequest) {
    // TODO: NOT IMPLEMENTED
    log.warn("validateUpdateMenuItemRequest NOT IMPLEMENTED");
  }
}
