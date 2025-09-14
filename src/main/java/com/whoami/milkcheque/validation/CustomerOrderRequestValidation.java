package com.whoami.milkcheque.validation;

import com.whoami.milkcheque.dto.request.CustomerOrderPatchRequest;
import com.whoami.milkcheque.exception.InvalidRequest;
import com.whoami.milkcheque.exception.MenuItemRetrievalException;
import com.whoami.milkcheque.model.CustomerModel;
import com.whoami.milkcheque.model.MenuItemModel;
import com.whoami.milkcheque.model.SessionModel;
import com.whoami.milkcheque.model.StoreModel;
import com.whoami.milkcheque.repository.CustomerRepository;
import com.whoami.milkcheque.repository.MenuItemRepository;
import com.whoami.milkcheque.repository.SessionRepository;
import com.whoami.milkcheque.repository.StoreRepository;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class CustomerOrderRequestValidation {

  private final SessionRepository sessionRepository;
  private final StoreRepository storeRepository;
  private final CustomerRepository customerRepository;
  private final MenuItemRepository menuItemRepository;

  private CustomerOrderRequestValidation(
      SessionRepository sessionRepository,
      StoreRepository storeRepository,
      MenuItemRepository menuItemRepository,
      CustomerRepository customerRepository) {
    this.storeRepository = storeRepository;
    this.menuItemRepository = menuItemRepository;
    this.customerRepository = customerRepository;
    this.sessionRepository = sessionRepository;
  }

  public void validateOrderItemExistInStore(Long itemId, Long storeId) {
    Optional<MenuItemModel> menuItem = menuItemRepository.findById(itemId);
    if (!menuItem.isPresent())
      throw new MenuItemRetrievalException(
          "-6", MessageFormat.format("menu item {0} not found", itemId));
    if (menuItem.get().getStoreModel().getStoreId() != storeId)
      throw new MenuItemRetrievalException(
          "-7", MessageFormat.format("menu item {0} not found for store", itemId));
  }

  public void validateAllOrderItemsInStore(Map<Long, Long> orderItems, Long storeId) {
    orderItems
        .entrySet()
        .forEach(
            item -> {
              validateOrderItemExistInStore(item.getKey(), storeId); // Validate by mitem_id
            });
  }

  public void validateCustomerSessionStoreRelation(Long customerId, Long sessionId, Long storeId) {
    Optional<CustomerModel> customer = customerRepository.findById(customerId);
    Optional<SessionModel> session = sessionRepository.findById(sessionId);
    Optional<StoreModel> store = storeRepository.findById(storeId);

    if (!customer.isPresent())
      throw new InvalidRequest(
          "-1", MessageFormat.format("customerId {0} doesn't exist", customerId));
    if (!session.isPresent())
      throw new InvalidRequest(
          "-2", MessageFormat.format("sessionId {0} doesn't exist", sessionId));
    if (!store.isPresent())
      throw new InvalidRequest("-3", MessageFormat.format("storeId {0} doesn't exist", storeId));
    if (!session.get().getSessionCustomers().contains(customer.get()))
      throw new InvalidRequest(
          "-4",
          MessageFormat.format(
              "customer {0} is not registered in session {1}", customerId, sessionId));
    if (!session.get().getStoreModel().equals(store.get()))
      throw new InvalidRequest(
          "-5",
          MessageFormat.format("session {0} does not belong to store {1}", sessionId, storeId));
  }

  public void validateOrderPatchRequest(CustomerOrderPatchRequest customerOrderPatchRequest) {
    validateCustomerSessionStoreRelation(
        customerOrderPatchRequest.getCustomerId(),
        customerOrderPatchRequest.getSessionId(),
        customerOrderPatchRequest.getStoreId());
    validateAllOrderItemsInStore(
        customerOrderPatchRequest.getOrderItems(), customerOrderPatchRequest.getStoreId());
  }
}
