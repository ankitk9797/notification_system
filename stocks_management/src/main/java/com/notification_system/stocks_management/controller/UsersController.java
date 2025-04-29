package com.notification_system.stocks_management.controller;

import com.notification_system.stocks_management.dto.SubscribeRequest;
import com.notification_system.stocks_management.entity.Users;
import com.notification_system.stocks_management.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/create/{email}")
    public void createUser(@PathVariable String email){
        usersService.addUser(email);
    }

    @GetMapping("/list")
    public List<Users> list(){
        return usersService.list();
    }

    @GetMapping("/subscribed-stocks/{email}")
    public List<String> getSubscribedStocksByEmail(@PathVariable String email){
        return usersService.getSubscribedStocksByEmail(email);
    }

}
