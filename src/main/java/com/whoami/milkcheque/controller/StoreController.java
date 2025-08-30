package com.whoami.milkcheque.controller;


import com.whoami.milkcheque.dto.response.MenuItemResponse;
import com.whoami.milkcheque.dto.response.StoreInfo;
import com.whoami.milkcheque.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/info")
    public ResponseEntity<StoreInfo> getStoreInfo(@RequestParam Long storeId, @RequestParam Long tableId) {
        return storeService.getStoreInfo(storeId, tableId);
    }

    @GetMapping("/menu")
    public ResponseEntity<ArrayList<MenuItemResponse>> getMenu(@RequestParam Long storeId) {
        return storeService.getMenuItems(storeId);
    }
}
