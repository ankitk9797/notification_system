package com.notification_system.alert_system.service;

import com.notification_system.alert_system.repo.StocksRepository;
import com.notification_system.stocks_management.dto.StockPriceUpdate;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class StockPriceListener {

    private final EmailService emailService;
    private final StocksRepository stocksRepository;

    public StockPriceListener(EmailService emailService, StocksRepository stocksRepository) {
        this.emailService = emailService;
        this.stocksRepository = stocksRepository;
    }

    @Transactional
    @KafkaListener(topics = "stock-price-updates", groupId = "my-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void listen(StockPriceUpdate update) {
        List<String> emails = stocksRepository.findByName(update.getStockName()).get().getSubscribedUsers();
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("Notification sending......");
        emails.forEach(email->{
            executorService.submit(()->{
                emailService.sendPriceChangeEmail(email, update);
            });
        });
    }
}

