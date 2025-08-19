package com.whoami.milkcheque.validation;

import com.whoami.milkcheque.dto.StaffDTO;
import com.whoami.milkcheque.enums.AuthenticationStatus;
import com.whoami.milkcheque.exception.AuthenticationFormatException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;

import javax.naming.AuthenticationException;
import javax.swing.text.html.Option;
import java.util.Optional;

public class SignUpValidation {

    public void firstNameValidation(String name) throws AuthenticationFormatException {
        if(name!=null) {
            if(!name.matches("^[A-Za-z]+$")){
                throw new AuthenticationFormatException("Name must contain only letters", HttpStatus.BAD_REQUEST,AuthenticationStatus.InFirstName);
            }
        }
    }
    public void lastNameValidation(String name) throws AuthenticationFormatException {
        if(name!=null) {
            if(!name.matches("^[A-Za-z]+$")){
                throw new AuthenticationFormatException("Name must contain only letters",HttpStatus.BAD_REQUEST,AuthenticationStatus.InLastName);
            }

        }
    }

    public void emailValidation(String email) throws AuthenticationFormatException {
        if(email!=null) {

            if(!email.matches("/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$/")){
                throw new AuthenticationFormatException("Invalid e-mail",HttpStatus.BAD_REQUEST,AuthenticationStatus.InEmail);

            }
        }
    }

    public void ageValidation(int age) throws AuthenticationFormatException {
        Integer intAge= age;
        if(intAge!=null) {

            if( intAge< 0 || intAge > 100) {
                throw new AuthenticationFormatException("Invalid age",HttpStatus.BAD_REQUEST,AuthenticationStatus.InAge);

            }
        }
    }

    public void phoneNumberValidation(String phoneNumber){
        if(phoneNumber!=null) {
            if(!phoneNumber.matches("^[0-9]{10}$")){
                throw new AuthenticationFormatException("Invalid phone number",HttpStatus.BAD_REQUEST, AuthenticationStatus.InPhoneNumber);

            }
        }
    }

    public void passwordValidation(String password){
        if(password!=null) {
            if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$")){
                throw new AuthenticationFormatException("Invalid Password",HttpStatus.BAD_REQUEST,AuthenticationStatus.InPassword);

            }
        }
    }

    public void validateStaff(StaffDTO staffDTO) {
        firstNameValidation(staffDTO.getFirstName());
        lastNameValidation(staffDTO.getLastName());
        emailValidation(staffDTO.getEmail());
        ageValidation(staffDTO.getAge());
        phoneNumberValidation(staffDTO.getPhoneNumber());
        passwordValidation(staffDTO.getPassword());
    }

}
