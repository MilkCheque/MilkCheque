package com.whoami.milkcheque.service;


import com.whoami.milkcheque.dto.request.CustomerRequest;
import com.whoami.milkcheque.dto.response.LoginResponse;
import com.whoami.milkcheque.dto.request.CustomerOrderPatchRequest;
import com.whoami.milkcheque.dto.response.CustomerOrderPatchResponse;
import com.whoami.milkcheque.exception.LoginProcessFailureException;
import com.whoami.milkcheque.exception.AddOrderPatchFailureException;
import com.whoami.milkcheque.mapper.Mapper;
import com.whoami.milkcheque.model.CustomerModel;
import com.whoami.milkcheque.model.CustomerOrderModel;
import com.whoami.milkcheque.model.StoreTableModel;
import com.whoami.milkcheque.model.OrderItemModel;
import com.whoami.milkcheque.model.MenuItemModel;
import com.whoami.milkcheque.model.StoreModel;
import com.whoami.milkcheque.model.SessionModel;
import com.whoami.milkcheque.repository.CustomerRepository;
import com.whoami.milkcheque.repository.CustomerOrderRepository;
import com.whoami.milkcheque.repository.SessionRepository;
import com.whoami.milkcheque.repository.OrderItemRepository;
import com.whoami.milkcheque.repository.StoreTableRepository;
import com.whoami.milkcheque.repository.StoreRepository;
import com.whoami.milkcheque.repository.MenuItemRepository;
import com.whoami.milkcheque.validation.AuthenticationValidation;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class SessionSerivce {
    StoreRepository storeRepository;
    AuthenticationValidation authenticationValidation;
    CustomerRepository customerRepository;
    CustomerOrderRepository customerOrderRepository;
    SessionRepository sessionRepository;
    StoreTableRepository storeTableRepository;
    MenuItemRepository menuItemRepository;
    OrderItemRepository orderItemRepository;

    SessionSerivce(AuthenticationValidation authenticationValidation,
                   CustomerRepository customerRepository,
                   CustomerOrderRepository customerOrderRepository,
                   SessionRepository sessionRepository,
                   StoreRepository storeRepository,
                   StoreTableRepository storeTableRepository,
                   OrderItemRepository orderItemRepository,
                   MenuItemRepository menuItemRepository) {
        this.authenticationValidation = authenticationValidation;
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
            validateOrderPatchRequest(customerOrderPatchRequest);
            
            Optional<CustomerModel> customer = customerRepository
                                     .findById(customerOrderPatchRequest.getCustomerId());
            Optional<SessionModel> session = sessionRepository
                                   .findById(customerOrderPatchRequest.getSessionId());
            Optional<CustomerOrderModel> customerOrder = customerOrderRepository.findByCustomerModelAndSessionModel(customer.get(), session.get());
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
                // This check can probalby be removed if we rely on the validation method to check for existance first
                if (!menuItem.isPresent())  {
                    String exceptionString = "item id %d, not found";
                    throw new AddOrderPatchFailureException("-2", String.format(exceptionString, itemId));
                }
                    
                OrderItemModel orderItemModel = new OrderItemModel();
                orderItemModel.setCustomerOrderModel(customerOrder.get());
                orderItemModel.setMenuItemModel(menuItem.get());
                orderItemModel.setQuantity(itemQuantity);
                orderItemsToAdd.add(orderItemModel);
            }
            for (OrderItemModel orderItem : orderItemsToAdd)
                orderItemRepository.save(orderItem);
            customerOrderRepository.save(customerOrder.get());
            // TODO: There should be an observer on the vendor side, 
            // to get updated with the new request patch of ordered items
            return ResponseEntity.ok().body(new CustomerOrderPatchResponse(
                        "0",
                        "order patch added"));
        }
        catch (AddOrderPatchFailureException e) {
            throw new AddOrderPatchFailureException(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            throw new AddOrderPatchFailureException("-1", e.getMessage());
        }
    }

    private void validateOrderPatchRequest(
            CustomerOrderPatchRequest customerOrderPatchRequest)  {
        authenticationValidation.validateOrderPatchRequest(customerOrderPatchRequest);
    }

    public ResponseEntity<LoginResponse> addCustomer(CustomerRequest customerRequest) {
        try {
            validateCustomerLoginRequest(customerRequest);
            Mapper mapper = new Mapper();
            CustomerModel customerModel = mapper.convertCustomerRequestToCustomerModel(customerRequest);
            customerRepository.save(customerModel);
            String token = null;
            createSession(customerRequest.getTableId(),customerModel.getCustomerId(), customerRequest.getStoreId());

            return ResponseEntity.ok().body(new LoginResponse("1","login success",token));
        }
        catch(Exception e) {
            throw new LoginProcessFailureException("-1", e.getMessage());
        }

    }

    private void validateCustomerLoginRequest(CustomerRequest customerRequest)  {
        authenticationValidation.validateCustomerRequest(customerRequest);
    }

    private void createSession(Long tableId, Long customerId, Long storeId) {
        Optional<SessionModel> optionalSessionModel = sessionRepository.findByStoreTableModel_StoreTableId(tableId);
        if(optionalSessionModel.isPresent()){
            addCustomerToSession(tableId,customerId);
            return;
        }
        SessionModel sessionModel = new SessionModel();
        StoreTableModel storeTableModel = storeTableRepository.findById(tableId).get();
        CustomerModel customerModel = customerRepository.findById(customerId).get();
        StoreModel storeModel = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

//        sessionModel.setSessionId(2L);
        sessionModel.getSessionCustomers().add(customerModel);
        sessionModel.setStoreTableModel(storeTableModel);
        sessionModel.setStoreModel(storeModel);
        sessionRepository.save(sessionModel);
        //customerModel.getCustomerSessions().add(sessionModel);
        //customerRepository.save(customerModel);
    }

    private void addCustomerToSession(Long tableId, Long customerId) {
        SessionModel sessionModel = sessionRepository.getByStoreTableModel_StoreTableId(tableId);
//        if (sessionModel == null) {
//            throw new RuntimeException("No session found for tableId " + tableId);
//        }
        CustomerModel customerModel = customerRepository.findById(customerId).get();
        sessionModel.getSessionCustomers().add(customerModel);
        sessionRepository.save(sessionModel);
        //customerModel.getCustomerSessions().add(sessionModel);
        //customerRepository.save(customerModel);
    }

//    private boolean isCustomerInSession(Long customerId) {
//
//    }



}
