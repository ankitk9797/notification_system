package com.notification_system.api_gateway.config;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

@Configuration
public class Routes {
    @Bean
    public RouterFunction<ServerResponse> stocksManagementRoute(){
        return GatewayRouterFunctions.route("stocks-management")
                .route(RequestPredicates.path("/api/stocks/**"), HandlerFunctions.http("http://localhost:9094"))
                .route(RequestPredicates.path("/api/users/**"), HandlerFunctions.http("http://localhost:9094"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userManagementRoute(){
        return GatewayRouterFunctions.route("user-management")
                .route(RequestPredicates.path("/api/user/**"), HandlerFunctions.http("http://localhost:9091"))
                .build();
    }
}
