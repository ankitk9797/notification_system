package com.notification_system.stocks_management.dto;

import lombok.Data;

@Data
public class SubscribeRequest {
    private String stock;
    private String email;
}
