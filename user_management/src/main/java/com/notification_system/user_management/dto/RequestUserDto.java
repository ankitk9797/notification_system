package com.notification_system.user_management.dto;

import lombok.Data;

@Data
public class RequestUserDto {
    private String name;
    private String email;
    private String password;
}
