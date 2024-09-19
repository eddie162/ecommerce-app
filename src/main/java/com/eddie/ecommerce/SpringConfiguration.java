package com.eddie.ecommerce;

import com.eddie.ecommerce.service.mappers.CartMapper;
import com.eddie.ecommerce.service.mappers.ProductMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class SpringConfiguration {

    @Bean
    protected ProductMapper productMapper() {
        return new ProductMapper();
    }

    @Bean
    protected CartMapper cartMapper() {
        return new CartMapper();
    }

    @Bean
    protected Clock clock(){
        return Clock.systemUTC();
    }
}
