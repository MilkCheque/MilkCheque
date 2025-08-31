package com.whoami.milkcheque.service;


import com.whoami.milkcheque.dto.request.CustomerRequest;
import com.whoami.milkcheque.dto.response.LoginResponse;
import com.whoami.milkcheque.dto.request.CustomerOrderPatchRequest;
import com.whoami.milkcheque.dto.response.CustomerOrderPatchResponse;
import com.whoami.milkcheque.exception.LoginProcessFailureException;
import com.whoami.milkcheque.exception.AddOrderPatchFailureException;
import com.whoami.milkcheque.mapper.Mapper;
import com.whoami.milkcheque.model.*;
import com.whoami.milkcheque.repository.*;
import com.whoami.milkcheque.validation.AuthenticationValidation;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class SessionSerivce {
    private final StoreRepository storeRepository;
    AuthenticationValidation authenticationValidation;
    CustomerRepository customerRepository;
    CustomerOrderRepository customerOrderRepository;
    SessionRepository sessionRepository;
    StoreTableRepository storeTableRepository;

    SessionSerivce(AuthenticationValidation authenticationValidation,
                   CustomerRepository customerRepository,
                   CustomerOrderRepository customerOrderRepository,
                   SessionRepository sessionRepository,
                   StoreTableRepository storeTableRepository, StoreRepository storeRepository) {
        this.authenticationValidation = authenticationValidation;
        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.sessionRepository = sessionRepository;
        this.storeTableRepository = storeTableRepository;
        this.storeRepository = storeRepository;
    }


    public ResponseEntity<CustomerOrderPatchResponse> addOrderPatch(
            CustomerOrderPatchRequest customerOrderPatchRequest) {
        try {
            validateOrderPatchRequest(customerOrderPatchRequest);
            
            //TODO: Use actual instances or use another query with only IDs
            CustomerModel customer = new CustomerModel();
            SessionModel session = new SessionModel();
            Optional<CustomerOrderModel> orderId = customerOrderRepository
                                                   .findByCustomerModelAndSessionModel(
                                                        customer,
                                                        session
                                                    );
            if (!orderId.isPresent()) {
                // TODO: Create new order and use it
            }
            Mapper mapper = new Mapper();
            List<Long> menuItemIds = customerOrderPatchRequest.getMenuItemIds();
            for (Long menuItemId : menuItemIds) {
                // Get 
                OrderItemModel orderItemModel;
                //customerRepository.save(customerModel);
                
            }
            // TODO: There should be an observer on the vendor side, 
            // to get updated with the new request patch of ordered items
            return ResponseEntity.ok().body(new CustomerOrderPatchResponse(
                        "0",
                        "order patch added"));
        }
        catch(Exception e) {
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
