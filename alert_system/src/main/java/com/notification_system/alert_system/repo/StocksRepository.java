package com.notification_system.alert_system.repo;

import com.notification_system.alert_system.entity.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StocksRepository extends JpaRepository<Stocks, Long> {

    Optional<Stocks> findByName(String name);

}
