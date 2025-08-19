package com.whoami.milkcheque.service;

import com.whoami.milkcheque.model.StaffModel;
import com.whoami.milkcheque.repository.StaffRepository;
import com.whoami.milkcheque.dto.StaffDTO;
import com.whoami.milkcheque.dto.CredentialsDTO;

import com.whoami.milkcheque.validation.AuthenticationValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StaffService {
    @Autowired
    StaffRepository staffRepository;


    AuthenticationValidation authenticationValidation = new AuthenticationValidation();

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository=staffRepository;
    }


    private StaffModel convertToEntity(StaffDTO staffDTO) {
        StaffModel staffModel = new StaffModel();

        staffModel.setFirstName(staffDTO.getFirstName());
        staffModel.setLastName(staffDTO.getLastName());
        staffModel.setEmail(staffDTO.getEmail());
        staffModel.setAge(staffDTO.getAge());
        staffModel.setPhoneNumber(staffDTO.getPhoneNumber());
        staffModel.setPassword(staffDTO.getPassword());
        return staffModel;


    }

    public void saveStaff(StaffDTO staffDTO) {
        StaffModel staffModel;

        SignUpValidation(staffDTO);
        staffModel = convertToEntity(staffDTO);
        staffRepository.save(staffModel);
    }

    public void SignUpValidation(StaffDTO staffDTO){
        authenticationValidation.validateStaffSignup(staffDTO);
    }

    public boolean checkCredential(CredentialsDTO credentialsDTO) {
        authenticationValidation.validateStaffLogin(credentialsDTO);

        Optional<StaffModel> staffModel = this.staffRepository.findByEmail(credentialsDTO.getEmail());
      
        if (!staffModel.isPresent()) 
          return false;

        return staffModel.get().getPassword().equals(credentialsDTO.getPassword());
    }
}
