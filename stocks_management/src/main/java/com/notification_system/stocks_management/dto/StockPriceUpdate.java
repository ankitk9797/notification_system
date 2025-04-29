package com.notification_system.stocks_management.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StockPriceUpdate {
    private String stockName;
    private double oldPrice;
    private double newPrice;
    private LocalDateTime timestamp;

    public String getKafkaMsg(){
        return "{\"stockName\": " + stockName + ", \"oldPrice\": " + oldPrice +
                ", \"newPrice\": " + newPrice + ", \"timestamp\": " + timestamp +"}";
    }
}
