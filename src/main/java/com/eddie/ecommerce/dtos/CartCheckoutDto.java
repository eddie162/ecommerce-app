package com.eddie.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.io.Serializable;

@Value
public class CartCheckoutDto implements Serializable {

    @JsonProperty("cart")
    CartDto cart;

    @JsonProperty("total_cost")
    Double totalCost;
}
