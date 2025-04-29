package com.notification_system.stocks_management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "stocks")
@Data
public class Stocks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private long stockId;

    private String name;

    private double price;

    @ElementCollection
    @CollectionTable(
            name = "stock_subscribers",
            joinColumns = @JoinColumn(name = "stock_id")
    )
    @Column(name = "subscribed_users")
    private List<String> subscribedUsers;
}
