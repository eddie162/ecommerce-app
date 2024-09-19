package com.eddie.yapily_exercise.dtos;

import com.eddie.yapily_exercise.models.CartItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link CartItem}
 */
@Value
public class CartItemDto implements Serializable {

    @JsonProperty("product_id")
    Long id;

    int quantity;
}