package com.eddie.yapily_exercise.models.dtos;

import com.eddie.yapily_exercise.models.Label;
import com.eddie.yapily_exercise.models.Product;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link Product}
 */
@Value
public class ProductDto implements Serializable {
    Long product_id;
    String name;
    Double price;
    String added_at;
    List<String> labels;
}