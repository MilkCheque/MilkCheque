package com.whoami.milkcheque.service;


import com.whoami.milkcheque.dto.request.CustomerRequest;
import com.whoami.milkcheque.dto.response.LoginResponse;
import com.whoami.milkcheque.exception.LoginProcessFailureException;
import com.whoami.milkcheque.mapper.Mapper;
import com.whoami.milkcheque.model.CustomerModel;
import com.whoami.milkcheque.model.SessionModel;
import com.whoami.milkcheque.model.TableModel;
import com.whoami.milkcheque.repository.CustomerRepository;
import com.whoami.milkcheque.repository.SessionRepository;
import com.whoami.milkcheque.repository.TableRepository;
import com.whoami.milkcheque.validation.AuthenticationValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionSerivce {
    AuthenticationValidation authenticationValidation;
    CustomerRepository customerRepository;
    SessionRepository sessionRepository;
    TableRepository tableRepository;

    SessionSerivce(AuthenticationValidation authenticationValidation,
                   CustomerRepository customerRepository,
                   SessionRepository sessionRepository,
                   TableRepository tableRepository) {
        this.authenticationValidation = authenticationValidation;
        this.customerRepository = customerRepository;
        this.sessionRepository = sessionRepository;
        this.tableRepository = tableRepository;
    }

    public ResponseEntity<LoginResponse> addCustomer(CustomerRequest customerRequest) {
        try {
            customerLoginValidation(customerRequest);
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

    private void customerLoginValidation(CustomerRequest customerRequest)  {
        authenticationValidation.validateCustomerRequest(customerRequest);
    }

    private void createSession(Long tableId, Long customerId){
        boolean isTableIdExists=sessionRepository.existsById(tableId);
        Optional<SessionModel> optionalSessionModel =sessionRepository.findByTableModel_TableId(tableId);
        if(optionalSessionModel.isPresent()){
            addCustomerToSession(customerId,tableId);
            return;
        }
        SessionModel sessionModel = new SessionModel();
        TableModel tableModel =tableRepository.findById(tableId).get();
        CustomerModel customerModel = customerRepository.findById(customerId).get();
//        customerModel.getCustomerSessions().add(sessionModel);
        sessionModel.getSessionSet().add(customerModel);
        sessionModel.setTableModel(tableModel);
        sessionRepository.save(sessionModel);
        customerModel.getCustomerSessions().add(sessionModel);
        customerRepository.save(customerModel);
    }

    private void addCustomerToSession(Long tableId, Long customerId) {
        SessionModel sessionModel = sessionRepository.getByTableModel_TableId(tableId);
        CustomerModel customerModel = customerRepository.findById(customerId).get();
        sessionModel.getSessionSet().add(customerModel);
        sessionRepository.save(sessionModel);
        customerModel.getCustomerSessions().add(sessionModel);
        customerRepository.save(customerModel);

    }

}
