package com.eddie.yapily_exercise.dtos;

import com.eddie.yapily_exercise.models.Product;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link Product}
 */
@Value
public class ProductDto implements Serializable {
    Long product_id;
    String name;
    Double price;
    String added_at;
    Set<String> labels;
}