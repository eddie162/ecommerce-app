package com.eddie.ecommerce;

import com.eddie.ecommerce.service.mappers.CartMapper;
import com.eddie.ecommerce.service.mappers.ProductMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
