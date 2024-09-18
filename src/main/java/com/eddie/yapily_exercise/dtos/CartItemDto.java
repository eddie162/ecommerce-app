package com.eddie.yapily_exercise.dtos;

import com.eddie.yapily_exercise.models.CartItem;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link CartItem}
 */
@Value
public class CartItemDto implements Serializable {
    Long product_id;
    int quantity;
}