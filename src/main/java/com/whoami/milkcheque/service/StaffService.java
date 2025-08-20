package com.whoami.milkcheque.service;

import com.whoami.milkcheque.mapper.StaffMapper;
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

    @Autowired
    AuthenticationValidation authenticationValidation ;

    StaffMapper staffMapper = new StaffMapper();

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository=staffRepository;
    }

    public void saveStaff(StaffDTO staffDTO) {
        StaffModel staffModel;

        signUpValidation(staffDTO);
        staffModel = staffMapper.convertToEntity(staffDTO);
        staffRepository.save(staffModel);
    }

    public void signUpValidation(StaffDTO staffDTO){
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
