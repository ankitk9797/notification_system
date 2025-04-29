package com.notification_system.stocks_management.service;

import com.notification_system.stocks_management.dto.StocksDto;
import com.notification_system.stocks_management.dto.SubscribeRequest;
import com.notification_system.stocks_management.entity.Stocks;
import com.notification_system.stocks_management.entity.Users;
import com.notification_system.stocks_management.mapper.StocksMapper;
import com.notification_system.stocks_management.repo.StocksRepository;
import com.notification_system.stocks_management.repo.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StocksService {
    @Autowired
    private StocksRepository stocksRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private StocksMapper mapper;

    public StocksDto createStock(StocksDto dto){
        Optional<Stocks> optional = stocksRepository.findByName(dto.getName());
        if(optional.isPresent())
            return null;
        return mapper.toDto(stocksRepository.save(mapper.toEntity(dto)));
    }

    public List<StocksDto> list(){
        return stocksRepository.findAll().stream().map((stock)-> mapper.toDto(stock)).collect(Collectors.toList());
    }

    public StocksDto get(String stockName){
        Optional<Stocks> optional = stocksRepository.findByName(stockName);
        if(optional.isEmpty())
            return null;
        return mapper.toDto(optional.get());
    }

    @Transactional
    public StocksDto updatePrice(StocksDto dto){
        Optional<Stocks> optional = stocksRepository.findByName(dto.getName());
        if(optional.isEmpty())
            return null;
        Stocks updatedStock =optional.get();
        updatedStock.setPrice(dto.getPrice());
        return mapper.toDto(stocksRepository.save(updatedStock));
    }

    public boolean deleteStock(String stockName){
        Optional<Stocks> optional = stocksRepository.findByName(stockName);
        if(optional.isEmpty())
            return false;
        stocksRepository.deleteById(optional.get().getStockId());
        return true;
    }

    @Transactional
    public boolean subscribeStock(SubscribeRequest dto){
        Optional<Stocks> optional = stocksRepository.findByName(dto.getStock());
        if(optional.isEmpty())
            return false;
        Stocks updatedStocks = optional.get();
        updatedStocks.getSubscribedUsers().add(dto.getEmail());

        Users user = usersRepository.findByEmail(dto.getEmail()).get();
        user.getSubscribedStocks().add(dto.getStock());
        return true;
    }

    @Transactional
    public boolean unsubscribeStock(SubscribeRequest dto){
        Optional<Stocks> optional = stocksRepository.findByName(dto.getStock());
        if(optional.isEmpty())
            return false;
        Stocks updatedStocks = optional.get();
        updatedStocks.getSubscribedUsers().remove(dto.getEmail());
        Users user = usersRepository.findByEmail(dto.getEmail()).get();
        user.getSubscribedStocks().remove(dto.getStock());
        return true;
    }
}
