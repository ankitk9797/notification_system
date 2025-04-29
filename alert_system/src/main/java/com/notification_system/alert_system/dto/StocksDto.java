package com.notification_system.alert_system.dto;

import lombok.Data;

import java.util.List;

@Data
public class StocksDto {
    private String name;

    private double price;

    private List<String> subscribedUsers;
}
