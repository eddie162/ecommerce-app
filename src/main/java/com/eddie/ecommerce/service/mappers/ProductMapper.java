package com.eddie.ecommerce.service.mappers;

import com.eddie.ecommerce.models.Label;
import com.eddie.ecommerce.models.Product;
import com.eddie.ecommerce.dtos.ProductDto;

import java.util.Set;
import java.util.stream.Collectors;

import static com.eddie.ecommerce.service.ProductService.PRODUCT_DATE_FORMAT;

public class ProductMapper implements DtoEntityMapper<ProductDto, Product> {
    public ProductDto entityToDto(Product product){
        Set<String> labelList = product.getLabels().stream()
                .map(Label::getLabelName)
                .collect(Collectors.toSet());

        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getAddedAt().format(PRODUCT_DATE_FORMAT), labelList);
    }

    public Product dtoToEntity(ProductDto productDto){
        return Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .build();
    }
}
