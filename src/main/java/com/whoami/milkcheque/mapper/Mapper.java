package com.whoami.milkcheque.mapper;

import com.whoami.milkcheque.dto.response.MenuItemResponse;
import com.whoami.milkcheque.dto.response.StoreInfo;
import com.whoami.milkcheque.dto.request.SignUpRequest;
import com.whoami.milkcheque.model.MenuItemModel;
import com.whoami.milkcheque.model.StaffModel;
import com.whoami.milkcheque.model.StoreModel;
import com.whoami.milkcheque.model.TableModel;

public class Mapper {

    public StaffModel convertStaffDTOToModel(SignUpRequest signUpRequest) {
        StaffModel staffModel = new StaffModel();

        staffModel.setName(signUpRequest.getName());
        staffModel.setEmail(signUpRequest.getEmail());
        staffModel.setDateOfBirth(signUpRequest.getDateOfBirth());
        staffModel.setPhoneNumber(signUpRequest.getPhoneNumber());
        staffModel.setPassword(signUpRequest.getPassword());
        return staffModel;
    }

    public SignUpRequest convertStaffModelToDto(StaffModel staffModel) {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName(staffModel.getName());
        signUpRequest.setEmail(staffModel.getEmail());
        signUpRequest.setDateOfBirth(staffModel.getDateOfBirth());
        signUpRequest.setPhoneNumber(staffModel.getPhoneNumber());
        signUpRequest.setPassword(staffModel.getPassword());
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
}
