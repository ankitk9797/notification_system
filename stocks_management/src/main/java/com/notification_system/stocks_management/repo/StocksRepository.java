package com.notification_system.stocks_management.repo;

import com.notification_system.stocks_management.entity.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StocksRepository extends JpaRepository<Stocks, Long> {

    Optional<Stocks> findByName(String name);

}
