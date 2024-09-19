package com.eddie.ecommerce.dtos;

import com.eddie.ecommerce.models.Cart;
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