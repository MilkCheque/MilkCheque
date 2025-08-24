package com.whoami.milkcheque.mapper;

import com.whoami.milkcheque.dto.StaffDTO;
import com.whoami.milkcheque.model.StaffModel;

public class StaffMapper {

    public StaffModel convertToEntity(StaffDTO staffDTO) {
        StaffModel staffModel = new StaffModel();

        staffModel.setName(staffDTO.getName());
        staffModel.setEmail(staffDTO.getEmail());
        staffModel.setDateOfBirth(staffDTO.getDateOfBirth());
        staffModel.setPhoneNumber(staffDTO.getPhoneNumber());
        staffModel.setPassword(staffDTO.getPassword());
        return staffModel;


    }

    public StaffDTO convertToDto(StaffModel staffModel) {
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setName(staffModel.getName());
        staffDTO.setEmail(staffModel.getEmail());
        staffDTO.setDateOfBirth(staffModel.getDateOfBirth());
        staffDTO.setPhoneNumber(staffModel.getPhoneNumber());
        staffDTO.setPassword(staffModel.getPassword());

        return staffDTO;

    }

}
