package com.whoami.milkcheque.controller;


import com.whoami.milkcheque.dto.MenuItemDTO;
import com.whoami.milkcheque.dto.StoreInfo;
import com.whoami.milkcheque.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/store")
public class StoreController {

    private final StoreService storService;
    public StoreController(StoreService storService) {
        this.storService = storService;
    }

    @GetMapping("/menu")
    public ResponseEntity<ArrayList<MenuItemDTO>> getMenu(@RequestParam int storeId, @RequestParam int tableId) {
        return storService.getMenuItems(storeId, tableId);


    }

    @GetMapping("/info")
    public ResponseEntity<StoreInfo> getStoreInfo(@RequestParam Long storeId) {
        return storService.getStoreInfo(storeId);

    }


}
