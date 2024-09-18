package com.eddie.yapily_exercise;

import com.eddie.yapily_exercise.repositories.ProductRepository;
import com.eddie.yapily_exercise.service.mappers.CartMapper;
import com.eddie.yapily_exercise.service.mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean
    protected ProductMapper productMapper() {
        return new ProductMapper();

    }

    @Bean
    protected CartMapper cartMapper(
            @Autowired ProductMapper productMapper,
            @Autowired ProductRepository productRepository) {
        return new CartMapper(productMapper, productRepository);
    }
}
