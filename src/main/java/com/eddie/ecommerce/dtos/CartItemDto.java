package com.eddie.ecommerce.dtos;

import com.eddie.ecommerce.models.CartItem;
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