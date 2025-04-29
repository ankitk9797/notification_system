package com.notification_system.stocks_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StocksManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocksManagementApplication.class, args);
	}

}
