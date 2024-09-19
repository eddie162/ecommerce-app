package com.eddie.ecommerce.service;

import com.eddie.ecommerce.TestContext;
import com.eddie.ecommerce.dtos.ProductDto;
import com.eddie.ecommerce.models.Label;
import com.eddie.ecommerce.models.Product;
import com.eddie.ecommerce.repositories.LabelRepository;
import com.eddie.ecommerce.repositories.ProductRepository;
import com.eddie.ecommerce.service.mappers.ProductMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.eddie.ecommerce.service.ProductService.PRODUCT_DATE_FORMAT;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest extends TestContext {

    @MockBean
    ProductRepository productRepository;

    @MockBean
    LabelRepository labelRepository;

    @Autowired
    ProductMapper productMapper;

    Clock fixedClock;
    ProductService productService;
    String addedAt;

    @BeforeEach
    void setup(){
        // Create a fixed clock with a predefined date
        this.fixedClock = Clock.fixed(Instant.parse("2001-01-01T00:00:00Z"), ZoneOffset.UTC);
        this.productService = new ProductService(productRepository, labelRepository, productMapper, fixedClock);
        this. addedAt = LocalDate.now(fixedClock).format(PRODUCT_DATE_FORMAT);
    }

    @Test
    void getAllProducts() {
        List<Product> list = List.of(Product.builder().id(1L).name("test1").price(1D).addedAt(LocalDate.now(fixedClock)).labels(Collections.EMPTY_SET).build(),
                Product.builder().id(2L).name("test2").price(2D).addedAt(LocalDate.now(fixedClock)).labels(Collections.EMPTY_SET).build(),
                Product.builder().id(3L).name("test3").price(3D).addedAt(LocalDate.now(fixedClock)).labels(Collections.EMPTY_SET).build());

        when(productRepository.findAll())
                .thenReturn(list);

        List<ProductDto> expected = List.of(new ProductDto(1L, "test1", 1D, addedAt, Collections.EMPTY_SET),
                new ProductDto(2L, "test2", 2D, addedAt, Collections.EMPTY_SET),
                new ProductDto(3L, "test3", 3D, addedAt, Collections.EMPTY_SET));
        List<ProductDto> actual = productService.getAllProducts();
        Assertions.assertEquals(expected, actual);

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getById() {
        Optional<Product> product = Optional.of(Product.builder()
                .id(1L)
                .name("test1")
                .price(1D)
                .addedAt(LocalDate.now(fixedClock))
                .labels(Set.of(Label.builder().id(1L).labelName("drinks").build()))
                .build());

        when(productRepository.findById(1L)).thenReturn(product);

        ProductDto expected = new ProductDto(1L, "test1", 1D, addedAt, Set.of("drinks"));
        ProductDto actual = productService.getById(1L);
        Assertions.assertEquals(expected, actual);

        verify(productRepository, times(1)).findById(1L);
    }


    @Test
    void addProduct() {
        Product product = Product.builder()
                .name("test1")
                .price(1D)
                .addedAt(LocalDate.now(fixedClock))
                .labels(Set.of(Label.builder().id(1L).labelName("drinks").build()))
                .build();

        when(productRepository.findByName("test1"))
                .thenReturn(null);

        when(labelRepository.findAllByLabelNameIn(Set.of("drinks")))
                .thenReturn(Set.of(Label.builder().id(1L).labelName("drinks").build()));

        when(labelRepository.findAll())
                .thenReturn(List.of(
                        Label.builder().id(1L).labelName("drinks").build(),
                        Label.builder().id(2L).labelName("food").build(),
                        Label.builder().id(3L).labelName("clothes").build(),
                        Label.builder().id(4L).labelName("limited").build()
                ));


        ProductDto expected = new ProductDto(null, "test1", 1D, addedAt, Set.of("drinks"));
        ProductDto result = productService.addProduct(expected);
        Assertions.assertEquals(expected, result);

        verify(productRepository, times(1)).save(any());
    }

    @Test
    void deleteProduct() {
        productService.deleteProduct(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }
}