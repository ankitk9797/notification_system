package com.notification_system.user_management.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

public interface StockClient {
    @PostExchange("/api/users/create/{email}")
    void createUser(@PathVariable String email);
}
