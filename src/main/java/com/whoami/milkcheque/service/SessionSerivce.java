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
import com.whoami.milkcheque.model.SessionModel;
import com.whoami.milkcheque.repository.CustomerRepository;
import com.whoami.milkcheque.repository.CustomerOrderRepository;
import com.whoami.milkcheque.repository.SessionRepository;
import com.whoami.milkcheque.repository.StoreTableRepository;
import com.whoami.milkcheque.validation.AuthenticationValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class SessionSerivce {
    AuthenticationValidation authenticationValidation;
    CustomerRepository customerRepository;
    CustomerOrderRepository customerOrderRepository;
    SessionRepository sessionRepository;
    StoreTableRepository storeTableRepository;

    SessionSerivce(AuthenticationValidation authenticationValidation,
                   CustomerRepository customerRepository,
                   CustomerOrderRepository customerOrderRepository,
                   SessionRepository sessionRepository,
                   StoreTableRepository storeTableRepository) {
        this.authenticationValidation = authenticationValidation;
        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.sessionRepository = sessionRepository;
        this.storeTableRepository = storeTableRepository;
    }

    public ResponseEntity<LoginResponse> addCustomer(CustomerRequest customerRequest) {
        try {
            validateCustomerLoginRequest(customerRequest);
            Mapper mapper = new Mapper();
            CustomerModel customerModel = mapper.convertCustomerRequestToCustomerModel(customerRequest);
            customerRepository.save(customerModel);
            String token = null;
            createSession(customerRequest.getTableId(),customerModel.getCustomerId());

            return ResponseEntity.ok().body(new LoginResponse("1","login success",token));
        }
        catch(Exception e) {
            throw new LoginProcessFailureException("-1", e.getMessage());
        }

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

    private void validateCustomerLoginRequest(CustomerRequest customerRequest)  {
        authenticationValidation.validateCustomerRequest(customerRequest);
    }

    private void createSession(Long tableId, Long customerId){
        boolean isTableIdExists=sessionRepository.existsById(tableId);
        Optional<SessionModel> optionalSessionModel = sessionRepository.findByStoreTableModel_StoreTableId(tableId);
        if(optionalSessionModel.isPresent()){
            addCustomerToSession(customerId,tableId);
            return;
        }
        SessionModel sessionModel = new SessionModel();
        StoreTableModel storeTableModel = storeTableRepository.findById(tableId).get();
        CustomerModel customerModel = customerRepository.findById(customerId).get();
//        customerModel.getCustomerSessions().add(sessionModel);
        sessionModel.getSessionCustomers().add(customerModel);
        sessionModel.setStoreTableModel(storeTableModel);
        sessionRepository.save(sessionModel);
        customerModel.getCustomerSessions().add(sessionModel);
        customerRepository.save(customerModel);
    }

    private void addCustomerToSession(Long tableId, Long customerId) {
        SessionModel sessionModel = sessionRepository.getByStoreTableModel_StoreTableId(tableId);
        CustomerModel customerModel = customerRepository.findById(customerId).get();
        sessionModel.getSessionCustomers().add(customerModel);
        sessionRepository.save(sessionModel);
        customerModel.getCustomerSessions().add(sessionModel);
        customerRepository.save(customerModel);
    }
}
