package com.eddie.yapily_exercise.dtos;

import com.eddie.yapily_exercise.models.Cart;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link Cart}
 */
@Value
public class CartDto implements Serializable {
    Long cart_id;
    Set<CartItemDto> products;
    Boolean checked_out;
}