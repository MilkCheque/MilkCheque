package com.whoami.milkcheque.mapper;

import com.whoami.milkcheque.dto.StoreInfo;
import com.whoami.milkcheque.model.StoreModel;

public class StoreInfoMapper {
    public StoreInfo convertToDto(StoreModel storeModel) {
        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setStoreName(storeModel.getStoreName());
        storeInfo.setStoreLocation(storeModel.getStoreLocation());
        return storeInfo;
    }
}
