package com.notification_system.alert_system.controller;

import com.notification_system.alert_system.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/send")
public class AlertController {
    @Autowired
    EmailService emailService;
    @PostMapping
    public void send(){
//        emailService.sendPriceChangeEmail();
    }

}
