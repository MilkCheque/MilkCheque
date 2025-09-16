package com.whoami.milkcheque.service;

import com.whoami.milkcheque.dto.request.CustomerOrderPatchRequest;
import com.whoami.milkcheque.dto.request.CustomerRequest;
import com.whoami.milkcheque.dto.response.AllOrdersResponse;
import com.whoami.milkcheque.dto.response.CustomerOrderPatchResponse;
import com.whoami.milkcheque.dto.response.CustomerResponse;
import com.whoami.milkcheque.dto.response.OrderItemInfo;
import com.whoami.milkcheque.exception.AddOrderPatchFailureException;
import com.whoami.milkcheque.exception.LoginProcessFailureException;
import com.whoami.milkcheque.exception.MenuItemRetrievalException;
import com.whoami.milkcheque.exception.OrdersRetrievalException;
import com.whoami.milkcheque.mapper.Mapper;
import com.whoami.milkcheque.model.CustomerModel;
import com.whoami.milkcheque.model.CustomerOrderModel;
import com.whoami.milkcheque.model.MenuItemModel;
import com.whoami.milkcheque.model.OrderItemModel;
import com.whoami.milkcheque.model.SessionModel;
import com.whoami.milkcheque.model.StoreModel;
import com.whoami.milkcheque.model.StoreTableModel;
import com.whoami.milkcheque.repository.CustomerOrderRepository;
import com.whoami.milkcheque.repository.CustomerRepository;
import com.whoami.milkcheque.repository.MenuItemRepository;
import com.whoami.milkcheque.repository.OrderItemRepository;
import com.whoami.milkcheque.repository.SessionRepository;
import com.whoami.milkcheque.repository.StoreRepository;
import com.whoami.milkcheque.repository.StoreTableRepository;
import com.whoami.milkcheque.validation.AuthenticationValidation;
import com.whoami.milkcheque.validation.CustomerOrderRequestValidation;
import java.util.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SessionSerivce {
  StoreRepository storeRepository;
  AuthenticationValidation authenticationValidation;
  CustomerOrderRequestValidation customerOrderRequestValidation;
  CustomerRepository customerRepository;
  CustomerOrderRepository customerOrderRepository;
  SessionRepository sessionRepository;
  StoreTableRepository storeTableRepository;
  MenuItemRepository menuItemRepository;
  OrderItemRepository orderItemRepository;

  SessionSerivce(
      AuthenticationValidation authenticationValidation,
      CustomerOrderRequestValidation customerOrderRequestValidation,
      CustomerRepository customerRepository,
      CustomerOrderRepository customerOrderRepository,
      SessionRepository sessionRepository,
      StoreRepository storeRepository,
      StoreTableRepository storeTableRepository,
      OrderItemRepository orderItemRepository,
      MenuItemRepository menuItemRepository) {
    this.authenticationValidation = authenticationValidation;
    this.customerOrderRequestValidation = customerOrderRequestValidation;
    this.customerRepository = customerRepository;
    this.customerOrderRepository = customerOrderRepository;
    this.sessionRepository = sessionRepository;
    this.storeTableRepository = storeTableRepository;
    this.storeRepository = storeRepository;
    this.orderItemRepository = orderItemRepository;
    this.menuItemRepository = menuItemRepository;
  }

  public ResponseEntity<CustomerOrderPatchResponse> addOrderPatch(
      CustomerOrderPatchRequest customerOrderPatchRequest) {
    try {
      customerOrderRequestValidation.validateOrderPatchRequest(customerOrderPatchRequest);

      Long customerId = customerOrderPatchRequest.getCustomerId();
      Long sessionId = customerOrderPatchRequest.getSessionId();
      Optional<CustomerModel> customer = customerRepository.findById(customerId);
      Optional<SessionModel> session = sessionRepository.findById(sessionId);
      Optional<CustomerOrderModel> customerOrder =
          customerOrderRepository.findByCustomerModelAndSessionModel(customer.get(), session.get());
      if (!customerOrder.isPresent()) {
        customerOrder = Optional.of(new CustomerOrderModel());
        customerOrder.get().setCustomerModel(customer.get());
        customerOrder.get().setSessionModel(session.get());
        customerOrderRepository.save(customerOrder.get());
      }
      Mapper mapper = new Mapper();
      Map<Long, Long> orderItems = customerOrderPatchRequest.getOrderItems();
      ArrayList<OrderItemModel> orderItemsToAdd = new ArrayList<>();
      for (Map.Entry<Long, Long> item : orderItems.entrySet()) {
        Long itemId = item.getKey();
        Long itemQuantity = item.getValue();
        // TODO: Make sure store_id matches after finding
        Optional<MenuItemModel> menuItem = menuItemRepository.findById(itemId);
        // This check can probalby be removed if we rely on the validation method to check for
        // existance first
        if (!menuItem.isPresent()) {
          String exceptionString = "item id %d, not found";
          throw new AddOrderPatchFailureException("-2", String.format(exceptionString, itemId));
        }

        OrderItemModel orderItemModel = new OrderItemModel();
        orderItemModel.setCustomerOrderModel(customerOrder.get());
        orderItemModel.setMenuItemModel(menuItem.get());
        orderItemModel.setQuantity(itemQuantity);
        orderItemsToAdd.add(orderItemModel);
      }
      for (OrderItemModel orderItem : orderItemsToAdd) orderItemRepository.save(orderItem);
      customerOrderRepository.save(customerOrder.get());
      // TODO: There should be an observer on the vendor side,
      // to get updated with the new request patch of ordered items
      return ResponseEntity.ok().body(new CustomerOrderPatchResponse("0", "order patch added"));
    } catch (AddOrderPatchFailureException e) {
      throw new AddOrderPatchFailureException(e.getCode(), e.getMessage());
    } catch (Exception e) {
      throw new AddOrderPatchFailureException("-1", e.getMessage());
    }
  }

  public ResponseEntity<CustomerResponse> addCustomer(CustomerRequest customerRequest) {
    try {
      validateCustomerLoginRequest(customerRequest);
      Mapper mapper = new Mapper();
      CustomerModel customerModel = mapper.convertCustomerRequestToCustomerModel(customerRequest);
      customerRepository.save(customerModel);
      Long customerId = customerModel.getCustomerId();
      String token = null;
      Long sessionId =
          createSession(
              customerRequest.getTableId(),
              customerModel.getCustomerId(),
              customerRequest.getStoreId());

      return ResponseEntity.ok()
          .body(
              new CustomerResponse(
                  customerId, sessionId, "1", "Customer added successfully", token));
    } catch (Exception e) {
      throw new LoginProcessFailureException("-1", e.getMessage());
    }
  }

  private void validateCustomerLoginRequest(CustomerRequest customerRequest) {
    authenticationValidation.validateCustomerRequest(customerRequest);
  }

  private Long createSession(Long tableId, Long customerId, Long storeId) {
    Optional<StoreTableModel> optionalStoreTable = storeTableRepository.findById(tableId);
    if (!optionalStoreTable.isPresent()) {
      throw new LoginProcessFailureException("-1", "Store table not found");
    }
    Optional<SessionModel> optionalSessionModel =
        sessionRepository.findByStoreTableModel_StoreTableId(tableId);
    if (optionalSessionModel.isPresent()) {
      return addCustomerToSession(tableId, customerId);
    }
    SessionModel sessionModel = new SessionModel();
    StoreTableModel storeTableModel = storeTableRepository.findById(tableId).get();
    CustomerModel customerModel = customerRepository.findById(customerId).get();
    StoreModel storeModel =
        storeRepository
            .findById(storeId)
            .orElseThrow(() -> new RuntimeException("Store not found"));

    sessionModel.getSessionCustomers().add(customerModel);
    sessionModel.setStoreTableModel(storeTableModel);
    sessionModel.setStoreModel(storeModel);
    sessionRepository.save(sessionModel);

    return sessionModel.getSessionId();
  }

  private Long addCustomerToSession(Long tableId, Long customerId) {
    SessionModel sessionModel = sessionRepository.getByStoreTableModel_StoreTableId(tableId);
    if (sessionModel == null) {
      throw new RuntimeException("No session found for tableId " + tableId);
    }
    CustomerModel customerModel = customerRepository.findById(customerId).get();
    sessionModel.getSessionCustomers().add(customerModel);
    sessionRepository.save(sessionModel);
    return sessionModel.getSessionId();
  }

  public ResponseEntity<ArrayList<AllOrdersResponse>> getAllOrders(Long sessionId) {
    try {
      Optional<SessionModel> sessionOptional = sessionRepository.findById(sessionId);
      if (!sessionOptional.isPresent()) {
        throw new MenuItemRetrievalException("-1", "Session not found (⸝⸝๑﹏๑⸝⸝)");
      }
      SessionModel sessionModel = sessionOptional.get();
      Set<CustomerModel> allCustomers = sessionModel.getSessionCustomers();
      ArrayList<AllOrdersResponse> allOrdersResponses = new ArrayList<>();

      for (CustomerModel customer : allCustomers) {
        Set<CustomerOrderModel> orders = new HashSet<>(customer.getOrdersSet());
        Map<Long, OrderItemInfo> itemMap = new HashMap<>();
        for (CustomerOrderModel order : orders) {
          for (OrderItemModel orderItem : new HashSet<>(order.getOrderItemsSet())) {
            MenuItemModel menuitemModel = orderItem.getMenuItemModel();
            Long menuItemId = menuitemModel.getMenuId();
            String menuItemName = menuitemModel.getMenuItemName();
            Long quantity = orderItem.getQuantity();
            Double price = menuitemModel.getMenuItemPrice();
            itemMap.merge(
                menuItemId,
                new OrderItemInfo(menuItemName, quantity, price),
                (existing, newItem) -> {
                  existing.setQuantiy(existing.getQuantity() + newItem.getQuantity());
                  return existing;
                });
          }
        }
        ArrayList<OrderItemInfo> orderItems = new ArrayList<>(itemMap.values());

        AllOrdersResponse allOrdersResponse =
            new AllOrdersResponse(
                customer.getCustomerId(), customer.getCustomerFirstName(), orderItems);
        allOrdersResponses.add(allOrdersResponse);
      }
      return ResponseEntity.ok().body(allOrdersResponses);

    } catch (Exception e) {
      throw new OrdersRetrievalException(e.getMessage());
    }
  }
  // customer won't be able to scan another qr code anyway
  //  private boolean isCustomerInSession(Long customerId) {
  //    Optional<CustomerModel> customerModel = customerRepository.findById(customerId);
  //
  //    if (!customerModel.isPresent()) {
  //      return false;
  //    }
  //    List<SessionModel> allSessions = sessionRepository.findAllByCustomerId(customerId);
  //    for (SessionModel sessionModel : allSessions) {
  //      if (sessionModel.isSessionActive()) {
  //        return false;
  //      }
  //    }
  //    return true;
  //  }

}
