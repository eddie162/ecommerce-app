package com.eddie.ecommerce.dtos;

import com.eddie.ecommerce.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link Product}
 */
@Value
public class ProductDto implements Serializable {
    @JsonProperty("product_id")
    Long id;

    String name;

    Double price;

    @JsonProperty("added_at")
    String addedAt;

    Set<String> labels;
}