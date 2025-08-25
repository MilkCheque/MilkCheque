package com.whoami.milkcheque.controller;


import com.whoami.milkcheque.model.MenuItemModel;
import com.whoami.milkcheque.service.MenuService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/items")
    public Optional<MenuItemModel> getMenu(@RequestParam int storeId, @RequestParam int tableId) {
        System.out.println("MEEN HENA");

        return menuService.getMenuItems(storeId, tableId);

    }


}
