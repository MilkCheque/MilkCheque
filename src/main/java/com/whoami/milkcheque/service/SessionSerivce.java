package com.whoami.milkcheque.service;

import com.whoami.milkcheque.dto.request.CustomerOrderPatchRequest;
import com.whoami.milkcheque.dto.request.CustomerRequest;
import com.whoami.milkcheque.dto.response.CustomerOrderPatchResponse;
import com.whoami.milkcheque.dto.response.LoginResponse;
import com.whoami.milkcheque.exception.AddOrderPatchFailureException;
import com.whoami.milkcheque.exception.InvalidRequest;
import com.whoami.milkcheque.exception.LoginProcessFailureException;
import com.whoami.milkcheque.exception.MenuItemRetrievalException;
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
import java.text.MessageFormat;
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
        Optional<MenuItemModel> menuItem = menuItemRepository.findById(itemId);
        /* Only a race condition can cause this to happen,
         * we just checked for its existance with the validation method
         **/
        if (!menuItem.isPresent())
          throw new MenuItemRetrievalException(
              "-6", MessageFormat.format("menu item {0} not found", itemId));

        OrderItemModel orderItemModel = new OrderItemModel();
        orderItemModel.setCustomerOrderModel(customerOrder.get());
        orderItemModel.setMenuItemModel(menuItem.get());
        orderItemModel.setQuantity(itemQuantity);
        orderItemsToAdd.add(orderItemModel);
      }
      orderItemsToAdd.forEach(
          orderItem -> {
            orderItemRepository.save(orderItem);
          });
      // TODO: There should be an observer on the vendor side,
      // to signal for order updates
      return ResponseEntity.ok().body(new CustomerOrderPatchResponse("0", "order patch added"));
    } catch (MenuItemRetrievalException e) {
      throw new MenuItemRetrievalException(e.getCode(), e.getMessage());
    } catch (InvalidRequest e) {
      throw new InvalidRequest(e.getCode(), e.getMessage());
    } catch (Exception e) {
      throw new AddOrderPatchFailureException("-1", e.getMessage());
    }
  }

  public ResponseEntity<LoginResponse> addCustomer(CustomerRequest customerRequest) {
    try {
      validateCustomerLoginRequest(customerRequest);
      Mapper mapper = new Mapper();
      CustomerModel customerModel = mapper.convertCustomerRequestToCustomerModel(customerRequest);
      customerRepository.save(customerModel);
      String token = null;
      createSession(
          customerRequest.getTableId(),
          customerModel.getCustomerId(),
          customerRequest.getStoreId());

      return ResponseEntity.ok().body(new LoginResponse("1", "login success", token));
    } catch (Exception e) {
      throw new LoginProcessFailureException("-1", e.getMessage());
    }
  }

  private void validateCustomerLoginRequest(CustomerRequest customerRequest) {
    authenticationValidation.validateCustomerRequest(customerRequest);
  }

  private void createSession(Long tableId, Long customerId, Long storeId) {
    Optional<SessionModel> optionalSessionModel =
        sessionRepository.findByStoreTableModel_StoreTableId(tableId);
    if (optionalSessionModel.isPresent()) {
      addCustomerToSession(tableId, customerId);
      return;
    }
    SessionModel sessionModel = new SessionModel();
    StoreTableModel storeTableModel = storeTableRepository.findById(tableId).get();
    CustomerModel customerModel = customerRepository.findById(customerId).get();
    StoreModel storeModel =
        storeRepository
            .findById(storeId)
            .orElseThrow(() -> new RuntimeException("Store not found"));

    //        sessionModel.setSessionId(2L);
    sessionModel.getSessionCustomers().add(customerModel);
    sessionModel.setStoreTableModel(storeTableModel);
    sessionModel.setStoreModel(storeModel);
    sessionRepository.save(sessionModel);
    // customerModel.getCustomerSessions().add(sessionModel);
    // customerRepository.save(customerModel);
  }

  private void addCustomerToSession(Long tableId, Long customerId) {
    SessionModel sessionModel = sessionRepository.getByStoreTableModel_StoreTableId(tableId);
    //        if (sessionModel == null) {
    //            throw new RuntimeException("No session found for tableId " + tableId);
    //        }
    CustomerModel customerModel = customerRepository.findById(customerId).get();
    sessionModel.getSessionCustomers().add(customerModel);
    sessionRepository.save(sessionModel);
    // customerModel.getCustomerSessions().add(sessionModel);
    // customerRepository.save(customerModel);
  }

  //    private boolean isCustomerInSession(Long customerId) {
  //
  //    }

}
