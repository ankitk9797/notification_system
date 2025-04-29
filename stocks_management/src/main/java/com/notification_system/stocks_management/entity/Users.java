package com.notification_system.stocks_management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "users")
@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    @ElementCollection
    @CollectionTable(
            name = "users_subscribers",
            joinColumns = @JoinColumn(name = "id")
    )
    @Column(name = "subscribed_stocks")
    private List<String> subscribedStocks;
}
