package com.whoami.milkcheque.mapper;

import com.whoami.milkcheque.dto.request.CustomerRequest;
import com.whoami.milkcheque.dto.response.MenuItemResponse;
import com.whoami.milkcheque.dto.response.StoreInfo;
import com.whoami.milkcheque.dto.request.SignUpRequest;
import com.whoami.milkcheque.model.*;

public class Mapper {

    public StaffModel convertStaffDTOToModel(SignUpRequest signUpRequest) {
        StaffModel staffModel = new StaffModel();

        staffModel.setStaffFirstName(signUpRequest.getFirstName());
        staffModel.setStaffLastName(signUpRequest.getLastName());
        staffModel.setStaffEmail(signUpRequest.getEmail());
        staffModel.setStaffDOB(signUpRequest.getDOB());
        staffModel.setStaffPhone(signUpRequest.getPhone());
        staffModel.setStaffPassword(signUpRequest.getPassword());
        return staffModel;
    }

    public SignUpRequest convertStaffModelToDto(StaffModel staffModel) {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setFirstName(staffModel.getStaffFirstName());
        signUpRequest.setLastName(staffModel.getStaffLastName());
        signUpRequest.setEmail(staffModel.getStaffEmail());
        signUpRequest.setDOB(staffModel.getStaffDOB());
        signUpRequest.setPhone(staffModel.getStaffPhone());
        signUpRequest.setPassword(staffModel.getStaffPassword());
        return signUpRequest;
    }

    public MenuItemResponse convertMenuItemModelToDto(MenuItemModel menuItemModel) {
        MenuItemResponse menuItemResponse = new MenuItemResponse();
        menuItemResponse.setMenuItemId(menuItemModel.getMenuId());
        menuItemResponse.setMenuItemName(menuItemModel.getMenuItemName());
        menuItemResponse.setMenuItemDescription(menuItemModel.getMenuItemDescription());
        menuItemResponse.setPrice(menuItemModel.getMenuItemPrice());
        return menuItemResponse;
    }

    public MenuItemModel convertMenuItemDTOToModel(MenuItemResponse menuItemResponse) {
        MenuItemModel menuItemModel = new MenuItemModel();
        menuItemModel.setMenuId(menuItemResponse.getMenuItemId());
        menuItemModel.setMenuItemName(menuItemResponse.getMenuItemName());
        menuItemModel.setMenuItemDescription(menuItemResponse.getMenuItemDescription());
        menuItemModel.setMenuItemPrice(menuItemResponse.getPrice());
        return menuItemModel;
    }

    public StoreInfo convertStoreModelToStoreInfoDto(StoreModel storeModel, Long tableId) {
        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setStoreName(storeModel.getStoreName());
        storeInfo.setStoreLocation(storeModel.getStoreLocation());
        storeInfo.setTableId(tableId);
        return storeInfo;
    }

    public CustomerModel convertCustomerRequestToCustomerModel(CustomerRequest customerRequest) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerFirstName(customerRequest.getFirstName());
        customerModel.setCustomerLastName(customerRequest.getLastName());
        customerModel.setCustomerPhone(customerRequest.getPhone());
        return customerModel;
    }


}
