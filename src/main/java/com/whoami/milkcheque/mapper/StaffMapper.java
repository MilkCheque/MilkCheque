package com.whoami.milkcheque.mapper;

import com.whoami.milkcheque.dto.SignUpRequest;
import com.whoami.milkcheque.model.StaffModel;

public class StaffMapper {

    public StaffModel convertToEntity(SignUpRequest signUpRequest) {
        StaffModel staffModel = new StaffModel();

        staffModel.setName(signUpRequest.getName());
        staffModel.setEmail(signUpRequest.getEmail());
        staffModel.setDateOfBirth(signUpRequest.getDateOfBirth());
        staffModel.setPhoneNumber(signUpRequest.getPhoneNumber());
        staffModel.setPassword(signUpRequest.getPassword());
        return staffModel;


    }

    public SignUpRequest convertToDto(StaffModel staffModel) {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName(staffModel.getName());
        signUpRequest.setEmail(staffModel.getEmail());
        signUpRequest.setDateOfBirth(staffModel.getDateOfBirth());
        signUpRequest.setPhoneNumber(staffModel.getPhoneNumber());
        signUpRequest.setPassword(staffModel.getPassword());


        return signUpRequest;
    }

}
