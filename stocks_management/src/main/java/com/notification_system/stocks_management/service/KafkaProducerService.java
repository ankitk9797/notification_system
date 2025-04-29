package com.notification_system.stocks_management.service;

import com.notification_system.stocks_management.dto.StockPriceUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private String TOPIC = "stock-price-updates";

    @Autowired
    private KafkaTemplate<String, StockPriceUpdate> kafkaTemplate;

    public void sendMessage(StockPriceUpdate dto) {
        kafkaTemplate.send(TOPIC, dto.getStockName(), dto);
        System.out.println("Message sent to topic: " + dto.getKafkaMsg());
    }
}
