package com.whoami.milkcheque.validation;

import com.whoami.milkcheque.dto.request.CustomerRequest;
import com.whoami.milkcheque.dto.request.SignUpRequest;
import com.whoami.milkcheque.dto.request.LoginRequest;
import com.whoami.milkcheque.exception.*;
import com.whoami.milkcheque.model.StaffModel;
import com.whoami.milkcheque.repository.StaffRepository;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationValidation {
    
    private final StaffRepository staffRepository;
    private static final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])" +
                                         "(?=.*[A-Z])(?=.*[@#$%^&" +
                                         "-+=()])(?=\\S+$).{8,20}$";
    private static final String phoneRegex = "^(10|11|12|15)[0-9]{8}$";
    private static final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private AuthenticationValidation(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public void nameValidation(String name) {
        String message = null;
        if (name == null)
            message = "name is null";
        else if (name.isEmpty())
            message = "name field is empty";
        else if (!name.matches("^[A-Za-z]+$"))
            message = "name format invalid";
        if (message == null)
            return;
        throw new AuthenticationFormatException("-1", message);
    }

    public void phoneNumberValidation(String phoneNumber) {
        String message = null;
        if (phoneNumber == null)
            message = "phone number is null";
        else if (phoneNumber.isEmpty())
            message = "phone number field is empty";
        else if (!phoneNumber.matches(phoneRegex))
            message = "invalid phone number";
        try {
          if (phoneNumberExist(phoneNumber))
              message="phone number already exists";
        } catch (Exception e) {
            message = "phoneNumberExist: " + e.getMessage();
        }
        if (message == null) return;
        throw new AuthenticationFormatException("-1", message);
    }

    public void passwordValidation(String password) {
        String message = null;
        if (password == null)
            message = "password is null";
        if (password.isEmpty())
            message = "password field is empty";
        if (!password.matches(passwordRegex))
            message = "invalid password";
        if (message == null) return;
        throw new AuthenticationFormatException("-1", message);
    }

    public void emailValidation(String email) {
        String message = null;
        if (email == null)
            message = "email is null";
        if (email.isEmpty())
            message = "email field is empty";
        if (!email.matches(emailRegex))
            message = "invalid email";
        if (message == null) return;
        throw new AuthenticationFormatException("-1", message);
      
    }

    public boolean emailExist(String email) throws Exception {
        Optional<StaffModel> staffModel = staffRepository.findByEmail(email);
        return staffModel.isPresent();
    }

    public boolean phoneNumberExist(String phoneNumber) {
        String message = null;
        Optional<StaffModel> staffModel = staffRepository.findByPhoneNumber(
              phoneNumber);
        return staffModel.isPresent();
    }

    public void validateStaffSignup(SignUpRequest signUpRequest) {
        String message = null;
        nameValidation(signUpRequest.getName());
        emailValidation(signUpRequest.getEmail());
        //TODO: Validate DOB
        phoneNumberValidation(signUpRequest.getPhoneNumber());
        passwordValidation(signUpRequest.getPassword());
        emailValidation(signUpRequest.getEmail());
        try {
          if (emailExist(signUpRequest.getEmail()))
              message="email already exists";
        } catch (Exception e) {
            message = "emailExist: " + e.getMessage();
        }
        if (message != null)
            throw new AuthenticationFormatException("-1", message);
    }

    public void validateStaffLogin(LoginRequest loginRequest) 
        throws Exception {
        emailValidation(loginRequest.getEmail());
        if (loginRequest.getPassword() == null)
          throw new AuthenticationFormatException("-1", "password is null");
        Optional<StaffModel> staffModel = staffRepository .findByEmail(loginRequest .getEmail());
        if (!staffModel.isPresent() || 
            !staffModel.get().getPassword().
            equals(loginRequest.getPassword()))
          throw new AuthenticationFailureException("-1",
              "email or password don't match");

    }

    public void validateCustomerRequest(CustomerRequest customerRequest) {
        nameValidation(customerRequest.getCustomerName());
        phoneNumberValidation(customerRequest.getPhoneNumber());
    }
}
