package com.eddie.yapily_exercise.dtos;

import com.eddie.yapily_exercise.models.Cart;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link Cart}
 */
@Value
public class CartDto implements Serializable {
    @JsonProperty("cart_id")
    Long id;

    @JsonProperty("products")
    Set<CartItemDto> cartItems;

    @JsonProperty("checked_out")
    boolean checkedOut;
}