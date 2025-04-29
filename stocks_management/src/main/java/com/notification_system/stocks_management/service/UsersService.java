package com.notification_system.stocks_management.service;

import com.notification_system.stocks_management.dto.SubscribeRequest;
import com.notification_system.stocks_management.entity.Users;
import com.notification_system.stocks_management.repo.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public void addUser(String email){
        Users users = new Users();
        users.setEmail(email);

        usersRepository.save(users);
    }

    public List<Users> list(){
        return usersRepository.findAll();
    }

    public List<String> getSubscribedStocksByEmail(String email){
        return usersRepository.findByEmail(email).get().getSubscribedStocks();
    }

    @Transactional
    public boolean stockSubscribe(SubscribeRequest request){
        Optional<Users> optional = usersRepository.findByEmail(request.getEmail());
        if(optional.isEmpty())
            return false;
        Users users = optional.get();
        users.getSubscribedStocks().add(request.getStock());
        return true;
    }
}
