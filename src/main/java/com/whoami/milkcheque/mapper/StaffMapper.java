package com.whoami.milkcheque.mapper;

import com.whoami.milkcheque.dto.StaffDTO;
import com.whoami.milkcheque.model.StaffModel;

public class StaffMapper {

    public StaffModel convertToEntity(StaffDTO staffDTO) {
        StaffModel staffModel = new StaffModel();

        staffModel.setFirstName(staffDTO.getFirstName());
        staffModel.setLastName(staffDTO.getLastName());
        staffModel.setEmail(staffDTO.getEmail());
        staffModel.setAge(staffDTO.getAge());
        staffModel.setPhoneNumber(staffDTO.getPhoneNumber());
        staffModel.setPassword(staffDTO.getPassword());
        return staffModel;


    }

    public StaffDTO convertToDto(StaffModel staffModel) {
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setFirstName(staffModel.getFirstName());
        staffDTO.setLastName(staffModel.getLastName());
        staffDTO.setEmail(staffModel.getEmail());
        staffDTO.setAge(staffModel.getAge());
        staffDTO.setPhoneNumber(staffModel.getPhoneNumber());
        staffDTO.setPassword(staffModel.getPassword());

        return staffDTO;

    }

}
