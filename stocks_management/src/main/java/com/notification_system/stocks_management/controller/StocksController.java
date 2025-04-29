package com.notification_system.stocks_management.controller;

import com.notification_system.stocks_management.dto.StockPriceUpdate;
import com.notification_system.stocks_management.dto.StocksDto;
import com.notification_system.stocks_management.dto.SubscribeRequest;
import com.notification_system.stocks_management.service.KafkaProducerService;
import com.notification_system.stocks_management.service.StocksService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StocksController {

    @Autowired
    private StocksService stocksService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("/create")
    public ResponseEntity<?> createStock(@RequestBody StocksDto dto){
        StocksDto response_dto = stocksService.createStock(dto);
        if(response_dto!=null)
          return ResponseEntity.status(HttpStatus.CREATED).body(response_dto);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stock already exists");
    }

    @GetMapping("/list")
    public ResponseEntity<List<StocksDto>> list(){
        return ResponseEntity.status(HttpStatus.OK).body(stocksService.list());
    }

    @PostMapping("/update-price")
    public ResponseEntity<?> updatePrice(@RequestBody StocksDto dto){
        StocksDto oldStockData = stocksService.get(dto.getName());
        StocksDto response_dto = stocksService.updatePrice(dto);
        if(response_dto!=null) {
            StockPriceUpdate update =new StockPriceUpdate();
            update.setStockName(dto.getName());
            update.setOldPrice(oldStockData.getPrice());
            update.setNewPrice(dto.getPrice());
            update.setTimestamp(LocalDateTime.now());
            kafkaProducerService.sendMessage(update);
            return ResponseEntity.status(HttpStatus.CREATED).body(response_dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock not found");
    }

    @DeleteMapping("/delete/{stockName}")
    public ResponseEntity<?> deleteStock(@PathVariable String stockName){
        boolean isDeleted = stocksService.deleteStock(stockName);
        if(isDeleted)
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Stock deleted successfully");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock not found");
    }


    @PostMapping("/subscribe-stock")
    public ResponseEntity<?> subscribeStock(@RequestBody SubscribeRequest dto){
        boolean isSubscribed = stocksService.subscribeStock(dto);
        if(isSubscribed)
            return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/unsubscribe-stock")
    public ResponseEntity<?> unsubscribeStock(@RequestBody SubscribeRequest dto){
        boolean isUnSubscribed = stocksService.unsubscribeStock(dto);
        if(isUnSubscribed)
            return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
