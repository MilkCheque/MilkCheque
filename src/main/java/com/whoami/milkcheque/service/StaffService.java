package com.whoami.milkcheque.service;

import com.whoami.milkcheque.model.StaffModel;
import com.whoami.milkcheque.repository.CustomerRepository;
import com.whoami.milkcheque.repository.StaffRepository;
import dto.StaffDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {
    @Autowired
    StaffRepository staffRepository;

    public StaffService (StaffRepository staffRepository) {
        this.staffRepository=staffRepository;

    }


    private StaffModel convertToEntity(StaffDTO staffDTO) {
        StaffModel staffModel = new StaffModel();

        System.out.println("staffDTO: "+staffDTO.getFirstName());
        staffModel.setFirstName(staffDTO.getFirstName());
        staffModel.setLastName(staffDTO.getLastName());
        staffModel.setEmail(staffDTO.getEmail());
        staffModel.setAge(staffDTO.getAge());
        staffModel.setPhoneNumber(staffDTO.getPhoneNumber());
        staffModel.setPassword(staffDTO.getPassword());
        return staffModel;


    }

    public void saveStaff(StaffDTO staffDTO) {
        StaffModel staffModel=convertToEntity(staffDTO);
        System.out.println("staffModel: "+staffModel.getFirstName());
        staffRepository.save(staffModel);
    }



}
