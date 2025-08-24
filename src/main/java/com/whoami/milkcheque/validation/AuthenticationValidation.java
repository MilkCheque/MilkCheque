package com.whoami.milkcheque.validation;

import com.whoami.milkcheque.dto.CredentialsDTO;
import com.whoami.milkcheque.dto.StaffDTO;
import com.whoami.milkcheque.enums.AuthenticationStatus;
import com.whoami.milkcheque.exception.AuthenticationFormatException;
import com.whoami.milkcheque.model.StaffModel;
import com.whoami.milkcheque.repository.StaffRepository;
import com.whoami.milkcheque.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.Optional;

@Component
public class AuthenticationValidation {

    private final StaffRepository staffRepository;

    @Autowired
    public AuthenticationValidation(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public void nameValidation(String name) throws AuthenticationFormatException {
        String message = null;
        if (name == null)
            message = "Name is null";
        else if (name.isEmpty())
            message = "Name field is empty";
        else if (!name.matches("^[A-Za-z]+$"))
            message = "Name format invalid";
        if (message == null)
            return;
        throw new AuthenticationFormatException(message,HttpStatus.BAD_REQUEST,AuthenticationStatus.InEmail);
    }

    public void emailValidation(String email) throws AuthenticationFormatException {
        String message = null;
        if (email == null)
            message = "Email is null";
        else if (email.isEmpty())
            message = "Email field is empty";
        else if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
            message = "Invalid email format";
        if (message == null)
            return;
        throw new AuthenticationFormatException(message,HttpStatus.BAD_REQUEST,AuthenticationStatus.InEmail);
    }

    public void ageValidation(Integer age) throws AuthenticationFormatException {
        String message = null;
        if (age == null)
            message = "Age is null";
        else if (age < 0)
            message = "Oh you are in the void.. adorable ^_^";
        else if (age > 150)
            message = "<3 ربنا ياخدك";
        if (message == null)
            return;
        throw new AuthenticationFormatException(message,HttpStatus.BAD_REQUEST,AuthenticationStatus.InEmail);
    }

    public void phoneNumberValidation(String phoneNumber){
        String message = null;
        if (phoneNumber == null)
            message = "Phone number is null";
        else if (phoneNumber.isEmpty())
            message = "Phone number field is empty";
        else if (!phoneNumber.matches("^(10|11|12|15)[0-9]{8}$"))
            message = "Invalid phone number";
        if (message == null)
            return;
        throw new AuthenticationFormatException(message,HttpStatus.BAD_REQUEST,AuthenticationStatus.InEmail);
    }

    public void passwordValidation(String password){
        String message = null;
        if (password == null)
            message = "Password is null";
        if (password.isEmpty())
            message = "Password field is empty";
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$"))
            message = "Invalid password";
        if (message == null)
            return;
        throw new AuthenticationFormatException(message,HttpStatus.BAD_REQUEST,AuthenticationStatus.InEmail);
    }

    public void emailExists(String email) {
        String message = null;
        Optional<StaffModel> staffModel = this.staffRepository.findByEmail(email);

        if(staffModel.isPresent())
            message="Email already exists";

        if(message==null)
            return;
        throw new AuthenticationFormatException(message,HttpStatus.BAD_REQUEST,AuthenticationStatus.InEmail);

    }

    public void phoneNumberExists(StaffDTO staffDTO) {
        Optional<StaffModel> staffModel = this.staffRepository.findByPhoneNumber(staffDTO.getPhoneNumber());
        String message = null;
        if(staffModel.isPresent())
            message="Phone number already exists";
        if(message==null)
            return;
        throw new AuthenticationFormatException(message,HttpStatus.BAD_REQUEST,AuthenticationStatus.InEmail);
    }

    public void validateStaffSignup(StaffDTO staffDTO) {
        nameValidation(staffDTO.getName());
        emailValidation(staffDTO.getEmail());
//        ageValidation(staffDTO.getAge());
        phoneNumberValidation(staffDTO.getPhoneNumber());
        passwordValidation(staffDTO.getPassword());
        emailExists(staffDTO.getEmail());
        phoneNumberExists(staffDTO);
    }

    public void validateStaffLogin(CredentialsDTO credentialsDTO) {
        emailValidation(credentialsDTO.getEmail());
    }
}
