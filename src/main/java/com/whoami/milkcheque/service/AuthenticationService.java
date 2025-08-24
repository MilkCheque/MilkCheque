package com.whoami.milkcheque.service;

import com.whoami.milkcheque.mapper.StaffMapper;
import com.whoami.milkcheque.model.StaffModel;
import com.whoami.milkcheque.repository.StaffRepository;
import com.whoami.milkcheque.dto.SignUpRequest;
import com.whoami.milkcheque.dto.SignUpResponse;
import com.whoami.milkcheque.dto.LoginRequest;
import com.whoami.milkcheque.dto.LoginResponse;
import com.whoami.milkcheque.validation.AuthenticationValidation;
import com.whoami.milkcheque.exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.net.URI;

@Service
public class AuthenticationService {
    private final StaffRepository staffRepository;
    private final AuthenticationValidation authenticationValidation ;

    StaffMapper staffMapper = new StaffMapper();

    private AuthenticationService(StaffRepository staffRepository, AuthenticationValidation authenticationValidation) {
        this.staffRepository = staffRepository;
        this.authenticationValidation = authenticationValidation;
    }

    public ResponseEntity<SignUpResponse> signUpProcess(SignUpRequest signUpRequest) {
        try {
            signUpValidation(signUpRequest);
            StaffModel staffModel = staffMapper.convertToEntity(signUpRequest);
            staffRepository.save(staffModel);
            //TODO: add actual URI
            return ResponseEntity.created(new URI("id")).body(new SignUpResponse("0", "created"));
        } catch(Exception e) {
            throw new SignUpProcessFailureException("-1", e.getMessage());
        }
    }

    public ResponseEntity<LoginResponse> loginProcess(LoginRequest loginRequest) {
        try {
            authenticationValidation.validateStaffLogin(loginRequest);
            //TODO: Generate token
            String token = null;
            return ResponseEntity.ok().body(new LoginResponse("0", "login success", token));
        } catch(Exception e) {
            //TODO: Handle in global handler
            throw new LoginProcessFailureException("-1", e.getMessage());
        }
    }

    public void signUpValidation(SignUpRequest signUpRequest){
        authenticationValidation.validateStaffSignup(signUpRequest);
    }
}
