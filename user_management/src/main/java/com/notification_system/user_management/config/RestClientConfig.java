package com.notification_system.user_management.config;

import com.notification_system.user_management.client.StockClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;


@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    @Bean
    public StockClient inventoryClient() {
        String stockServiceUrl = "http://localhost:9092";

        RestClient restClient = RestClient.builder()
                .baseUrl(stockServiceUrl)
                .requestInterceptor((request, body, execution) -> {
                    SecurityContext context = SecurityContextHolder.getContext();
                    if (context != null && context.getAuthentication() != null) {
                        var principal = context.getAuthentication().getPrincipal();
                        if (principal instanceof Jwt jwt) {
                            request.getHeaders().add("Authorization", "Bearer " + jwt.getTokenValue());
                        }
                    }
                    return execution.execute(request, body);
                })
                .build();

        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(StockClient.class);
    }
}
