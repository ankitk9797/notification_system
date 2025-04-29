package com.notification_system.alert_system.service;


import com.notification_system.stocks_management.dto.StockPriceUpdate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPriceChangeEmail(String to, StockPriceUpdate dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Stock Price Alert: " + dto.getStockName());
        message.setText("The price for stock " + dto.getStockName() +
                " has changed from " + dto.getOldPrice() + " to " + dto.getNewPrice() +
                " at " + dto.getTimestamp());

        mailSender.send(message);
    }
}
